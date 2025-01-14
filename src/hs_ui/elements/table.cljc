(ns hs-ui.elements.table
  #?(:cljs
     (:require
       [reagent.core :as r]
       [clojure.string :as str]
       [goog.events :as events]
       [hs-ui.utils :as u]
       [hs-ui.components.tooltip]))
  #?(:clj
     (:require
       [clojure.string :as str]
       [hs-ui.utils :as u]
       [hs-ui.components.tooltip]))
  #?(:cljs
     (:import
       [goog.events EventType])))

(def root-class
  ["overflow-scroll"
   "table-fixed"
   "border-spacing-0"
   "border-separate"])

(def thead-class
  ["text-[var(--color-elements-assistive)]"])

(def column-name-class
  ["p-4"
   "pl-6"
   "font-medium"
   "text-nowrap"
   "text-left"
   "bg-[var(--color-surface-0)]"
   "border-b"
   "border-[var(--color-separator)]"

   "sticky"
   "top-0"])

(def column-value-class
  ["px-4"
   "py-2"
   "max-w-[3px]"
   "whitespace-nowrap"
   "truncate"
   "break-all"
   "overflow-hidden"
   "text-ellipsis"
   "whitespace-nowrap"])

(def body-tr-class
  ["even:bg-[var(--color-surface-1)]"
   "aria-selected:bg-[var(--color-surface-selected)]"
   "data-[role=link]:cursor-pointer"
   "data-[role=link]:hover:text-gray-400"
   "data-[role=link]:hover:bg-[var(--color-surface-1)]"])

(def local-store-key "hs-table-state")

(defn read-table-state!
  "Reads the entire table-state map from localStorage, returning a Clojure map or nil."
  [table-name]
  #?(:cljs
     (let [key (str local-store-key "-" table-name)]
       (when-let [json-str (js/localStorage.getItem key)]
         (try
           (js->clj (js/JSON.parse json-str) :keywordize-keys true)
           (catch :default _ nil))))
     :clj nil))

(defn write-table-state!
  "Writes the given portion of table-state (column widths, hidden columns, col order) to localStorage as JSON."
  [table-name new-state]

  #?(:cljs
     (let [key (str local-store-key "-" table-name)
           st  (select-keys new-state [:col-widths :col-hidden :col-index-to-model])]
       (js/localStorage.setItem key (js/JSON.stringify (clj->js st))))
     :clj nil))


(defn handle-drag-move
  [on-move]
  (fn [e]
    (.preventDefault e)
    (on-move (.-clientX e) (.-clientY e))))

(defn handle-drag-end
  [move-fn end-atom]
  (fn [e]
    #?(:cljs (events/unlisten js/window EventType.MOUSEMOVE move-fn))
    #?(:cljs (events/unlisten js/window EventType.MOUSEUP @end-atom))))

(defn initiate-drag!
  [on-move]
  (let [move-fn (handle-drag-move on-move)
        end-atom #?(:cljs (r/atom nil))]
    (reset! end-atom (handle-drag-end move-fn end-atom))
    #?(:cljs (events/listen js/window EventType.MOUSEMOVE move-fn))
    #?(:cljs (events/listen js/window EventType.MOUSEUP @end-atom))))

(defn extract-col-model [state-atom col-idx]
  (-> @state-atom :col-index-to-model (nth col-idx)))

(defn reorder-columns!
  [drag-idx drop-idx state-atom]
  (let [current-cols   (:col-index-to-model @state-atom)
        lower          (min drag-idx drop-idx)
        upper          (max drag-idx drop-idx)
        shift-dir      (if (< drag-idx drop-idx) :right :left)
        dragged-model  (extract-col-model state-atom drag-idx)]
    (swap! state-atom
           assoc
           :col-index-to-model
           (into []
                 (map-indexed
                   (fn [view-col col]
                     (cond
                       (= view-col drop-idx)
                       (current-cols drag-idx)

                       (or (< view-col lower)
                           (> view-col upper))
                       (current-cols view-col)

                       (and (>= view-col lower)
                            (= shift-dir :right))
                       (current-cols (inc view-col))

                       (and (<= view-col upper)
                            (= shift-dir :left))
                       (current-cols (dec view-col))))
                   current-cols)))))

(defn resizer-handle
  "Handle that resizes the column at `model-idx` in `state-atom` when dragged."
  [cell-ref model-idx state-atom table-name]
  [:span
   {:class        "inline-block w-2 absolute cursor-ew-resize h-full top-[30%] right-0 mr-[-12px] z-50"
    :on-click     #(.stopPropagation %)
    :on-mouse-down
    (fn [evt]
      (when-let [cell-node @cell-ref]
        (let [init-x     (.-clientX evt)
              init-width (.-clientWidth cell-node)]
          (initiate-drag!
           (fn [x _]
             (let [new-width (max 30 (- init-width (- init-x x)))]
               (swap! state-atom assoc-in
                      [:col-widths (keyword (str model-idx))] new-width)
               (aset cell-node "width" new-width)
               (write-table-state! table-name @state-atom))))
          (.preventDefault evt))))}
   "|"])

(defn header-cell
  [col-info visible-idx model-idx cfg state-atom]
  (let [st           @state-atom
        hidden-cols  (:col-hidden st)
        draggable?   (:draggable st)
        col-width    (get-in st [:col-widths (keyword (str model-idx))])
        cell-ref     #?(:cljs (r/atom nil))]
    [:th
     {:ref         (fn [el] (reset! cell-ref el))
      :class       column-name-class
      :draggable   draggable?
      :on-drag-start (fn [e]
                       (-> (.-dataTransfer e) (.setData "text/plain" ""))
                       (swap! state-atom assoc :col-reordering true))
      :on-drag-over  #(swap! state-atom assoc :col-hover visible-idx)
      :on-drag-end   (fn [_]
                       (let [hovered-col (:col-hover @state-atom)]
                         (when (not= visible-idx hovered-col)
                           (reorder-columns! visible-idx hovered-col state-atom)
                           (write-table-state! (:table-name cfg) @state-atom))
                         (swap! state-atom assoc
                                :col-hover nil
                                :col-reordering nil)))
      :width       (if col-width (str col-width "px") (:width col-info))
      :style       (merge
                     {:position "relative"
                      :cursor   (when draggable? "move")
                      :display  (when (get hidden-cols (keyword (str model-idx))) "none")}
                     (when (and (:col-reordering st)
                                (= visible-idx (:col-hover st)))
                       {:border-right "6px solid #3366CC"}))}

     [:span (:header col-info)]

     [resizer-handle cell-ref model-idx state-atom (:table-name cfg)]]))

(defn render-header-row
  [col-model cfg state-atom]
  [:tr
   (doall
    (map-indexed
     (fn [view-idx _]
       (let [model-idx (extract-col-model state-atom view-idx)
             info       (col-model model-idx)]
         ^{:key (or (:key info) model-idx)}
         [header-cell info view-idx model-idx cfg state-atom]))
     col-model))])

(defn resolve-cell-data
  [row cell-def]
  (let [{:keys [path expr]} cell-def]
    (or (and path (get-in row path))
        (and expr (expr row)))))

(defn render-data-row
  [row row-idx row-key-fn state-atom cfg]
  (let [st          @state-atom
        hidden-map  (:col-hidden st)
        col-key     (or (:col-key cfg) (fn [_ rn cn] cn))
        model       (:column-model cfg)]
    ^{:key (row-key-fn row row-idx)}
    [:tr {:class body-tr-class}
     (doall
      (map-indexed
       (fn [visible-idx _]
         (let [model-idx (extract-col-model state-atom visible-idx)
               value     (resolve-cell-data row (model model-idx))]
           ^{:key (col-key row row-idx model-idx)}
           [:td
            {:class column-value-class
             :style {:border-right (when (and (:col-reordering st)
                                              (= visible-idx (:col-hover st)))
                                     "2px solid #3366CC")
                     :display (when (get hidden-map (keyword (str model-idx))) "none")}}
            [hs-ui.components.tooltip/component
             {:place   "top"
              :class   (:c/tooltip-style cfg)
              :tooltip [:pre (or (:title value) (str (:value value)))]}
             (:value value)]]))
       (or model row)))]))

(defn render-all-rows
  [rows state-atom cfg]
  (let [row-key-fn (or (:row-key cfg) (fn [_ i] i))]
    (doall
     (map-indexed
      (fn [i row] (render-data-row row i row-key-fn state-atom cfg))
      rows))))

(defn column-visibility-ctrl
  "Renders a list of checkboxes or toggles that show/hide columns."
  [state-atom col-model table-name]
  (let [hidden-cols #?(:cljs (r/cursor state-atom [:col-hidden]))]
    [:ul {:class "flex"}
     (doall
      (map-indexed
       (fn [view-idx _]
         (let [model-idx   (extract-col-model state-atom view-idx)
               info        (col-model model-idx)
               hidden-cell #?(:cljs (r/cursor hidden-cols [(keyword (str model-idx))]))]
           ^{:key (or (:key info) model-idx)}
           [:li
            {:style    {:margin 8
                        :cursor "pointer"}
             :on-click (fn []
                         (swap! hidden-cell not)
                         (write-table-state! table-name @state-atom))}
            (:header info) " " (if @hidden-cell "☐" "☑")]))
       col-model))]))

(defn init-col-indices
  [headers]
  (into [] (map-indexed (fn [i _] i) headers)))

(defn core-table
  "Reagent component for rendering the <table> with header/body."
  []
  (fn [cfg col-model data state-atom]
    [:table (:table cfg)
     [:thead {:class thead-class}
      (render-header-row col-model cfg state-atom)]
     [:tbody (:tbody cfg)
      (render-all-rows data state-atom cfg)]]))

(defn generate-cols
  [cols-data]
  (mapv
   (fn [{:keys [name width]}]
     {:path   [(keyword name)]
      :header name
      :key    (keyword name)
      :width  width})
   cols-data))

(defn view
  [props]
  (let [col-defs    (generate-cols (:columns props))
        row-data    (:rows props)
        table-name  (or (:table-name props) "default")
        cfg         {:table           {:class (u/class-names root-class (:class props))}
                     :table-state     {:draggable (or (:draggable props) false)}
                     :column-model    col-defs
                     :c/tooltip-style (:c/tooltip-style props)
                     :table-name      table-name}
        local-state #?(:cljs (r/atom (:table-state cfg))
                       :clj  (atom (:table-state cfg)))]
    (swap! local-state assoc :col-index-to-model (init-col-indices col-defs))

    #?(:cljs
       (when-let [saved-state (read-table-state! table-name)]
         (swap! local-state merge saved-state))
       :clj nil)

    [:div {:class "w-max mt-2"}
     [:div
      (when (:visibility-ctrl props)
          [column-visibility-ctrl local-state col-defs table-name])
      [core-table cfg col-defs row-data local-state]]]))
