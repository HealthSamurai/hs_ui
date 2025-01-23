(ns hs-ui.elements.table
  (:require
   [clojure.string :as str]
   [hs-ui.utils :as utils]
   [hs-ui.components.tooltip]
   [hs-ui.components.list-item]
   [hs-ui.components.list-items]
   [hs-ui.organisms.checkbox]
   [hs-ui.svg.settings]
   #?(:cljs [reagent.core :as r])
   #?(:cljs [goog.events :as events]))
  #?(:cljs (:import [goog.events EventType])))

(def root-class
  ["table-fixed"
   "w-0"
   "border-spacing-0"
   "border-separate"])

(def thead-class
  ["sticky"
   "top-0"
   "text-[var(--color-elements-assistive)]"])

(def column-name-class
  ["group"
   "p-4"
   "font-medium"
   "text-nowrap"
   "text-left"
   "bg-[var(--color-surface-0)]"
   "border-b"
   "border-[var(--color-separator)]"
   "sticky"
   "top-0"])

(def column-value-class
  ["px-3"
   "py-2"
   "border-x-[0.25rem]"
   "border-transparent"
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
        end-atom (utils/ratom nil)]
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
  [:button
   {:class        "hidden active:text-[var(--color-cta)] group-hover:inline-block w-6 absolute cursor-ew-resize top-[30%] right-0 z-[5]"
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
               (write-table-state! table-name @state-atom))))
          (.preventDefault evt))))}
   "|"])

(def border-side-state (utils/ratom nil))
(def previous-visible-idx (utils/ratom nil))

(defn header-cell
  [col-info visible-idx model-idx cfg state-atom last-child last-column-width]
  (let [st           @state-atom
        hidden-cols  (:col-hidden st)
        draggable?   (:draggable st)
        col-width    (get-in st [:col-widths (keyword (str model-idx))])
        cell-ref     (utils/ratom nil)
        current-col-width (cond
                            col-width
                            (str col-width "px")

                            last-child (str @last-column-width "px")

                            (:width col-info)
                            (:width col-info)

                            :else "200px")]
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
                                :col-reordering nil)
                         (reset! border-side-state nil)))

      :style       (merge
                     {:position "relative"
                      :cursor   (when draggable? "move")
                      :display  (when (get hidden-cols (keyword (str model-idx))) "none")
                      :width current-col-width}
                     (when (and (:col-reordering st)
                                (= visible-idx (:col-hover st)))
                       {:border "0.25rem solid var(--color-cta)"
                        :padding "0.75rem"}))}

     [:span {:class "block overflow-hidden"}
      (:header col-info)]

     [resizer-handle cell-ref model-idx state-atom (:table-name cfg)]]))

(defn render-header-row
  [col-model cfg state-atom last-column-width]
  [:<>
   [:style "
     tr:has(button:active) button:not(:active) {display: none !important;}
     tr button:active{display: inline-block !important;}"]
   [:tr
    (doall
     (map-indexed
      (fn [view-idx _]
        (let [model-idx (extract-col-model state-atom view-idx)
              info       (col-model model-idx)]
          ^{:key (or (:key info) model-idx)}
          [header-cell info view-idx model-idx cfg state-atom (= view-idx (- (count col-model) 1)) last-column-width]))
      col-model))]])

(defn resolve-cell-data
  [row cell-def]
  (let [{:keys [path expr]} cell-def]
    (or (and path (get-in row path))
        (and expr (expr row)))))

(defn need-tooltip? [value]
  (not (cond (= value "false")
             true

             (= value "true")
             true

             (boolean? value)
             true

             (= value "-")
             true?

             (empty? value)
             true

             :else false)))

(defn render-data-row
  [row row-idx row-key-fn state-atom cfg]
  (let [st          @state-atom
        hidden-map  (:col-hidden st)
        col-key     (or (:col-key cfg) (fn [_ rn cn] cn))
        model       (:column-model cfg)
        on-row-click (:on-row-click cfg)]
    ^{:key (row-key-fn row row-idx)}
    [:tr {:class body-tr-class
          :data-role     (when on-row-click "link")
          :on-click      (when on-row-click #(on-row-click row))
          :aria-selected (:selected? row)}
     (doall
      (map-indexed
       (fn [visible-idx _]
         (let [model-idx (extract-col-model state-atom visible-idx)
               value     (resolve-cell-data row (model model-idx))]


           ^{:key (col-key row row-idx model-idx)}
           [:td
            {:class column-value-class
             :style (let [border-side (when (and (:col-reordering st)
                                                 (= visible-idx (:col-hover st))
                                                 (not= @previous-visible-idx (:col-hover st)))
                                        (if (> (:col-hover st) @previous-visible-idx) :border-right :border-left))
                          style (cond-> {:display (when (get hidden-map (keyword (str model-idx))) "none")}
                                  (and (:col-reordering st)
                                       (= visible-idx (:col-hover st))
                                       @border-side-state)
                                  (merge (if (= :border-right @border-side-state) {:border-right "0.25rem dashed var(--color-cta)"}
                                             {:border-left "0.25rem dashed var(--color-cta)"})))]
                      (when border-side (do (reset! previous-visible-idx (:col-hover st))
                                 (reset! border-side-state border-side)))
                      style)}
            (if (need-tooltip? (:value value))
              [hs-ui.components.tooltip/component
               {:class   (:c/tooltip-style cfg)
                :tooltip [:pre (or (:tooltip value) (str (:value value)))]}
               (:value value)]
              [:div (:value value)])]))
       (or model row)))]))

(defn render-all-rows
  [rows state-atom cfg]
  (let [row-key-fn (or (:row-key cfg) (fn [_ i] i))]
    (doall
     (map-indexed
      (fn [i row] (render-data-row row i row-key-fn state-atom cfg ))
      rows))))

(defn column-visibility-dropdown
  [state-atom col-model table-name]
  (let [menu-open? (utils/ratom false)
        on-click-outside (fn [e]
                           (when @menu-open?
                             (let [target (.-target e)]
                               (when (nil? (.closest target ".dropdown-container"))
                                 (reset! menu-open? false)))))]

    ;; :on-mous-down react event on element doen't work here for some reason
    ;; so had to add js/document listener
    #?(:cljs (.addEventListener js/document "mousedown" on-click-outside)
       :clj nil)

    (fn [state-atom col-model table-name]
      (let [hidden-cols #?(:cljs (r/cursor state-atom [:col-hidden])
                           :clj nil)]

        [:div {:class "relative inline-block w-full mt-2.5 dropdown-container"}
         [:div {:class "w-full flex justify-start"}
          [:div {:on-click #(swap! menu-open? not)
                 :class
                 (utils/class-names "text-[theme(colors.elements-assistive)] p-2 cursor-pointer flex content-center justify-center w-[32px] h-[32px] hover:rounded-[50%] hover:bg-[var(--color-separator)]"

                                          (if @menu-open? "rounded-[50%] bg-[var(--color-separator)]" ""))}
           hs-ui.svg.settings/svg]]
         (when @menu-open?
           [:div {:class "absolute right-0 mt-2 bg-white border shadow-md p-2 z-10"}
            [hs-ui.components.list-items/component {}
             (doall
              (map-indexed
               (fn [view-idx _]
                 (let [model-idx   (extract-col-model state-atom view-idx)
                       info        (col-model model-idx)
                       hidden-cell #?(:cljs (r/cursor hidden-cols [(keyword (str model-idx))])
                                      :clj nil)]

                   [hs-ui.components.list-item/component {:on-click (fn []
                                                                      (swap! hidden-cell not)
                                                                      (write-table-state! table-name @state-atom))}
                    [:div {:class "flex justify-between w-full"}
                     [:div {:class "mr-4"} (:header info)]
                     [hs-ui.organisms.checkbox/component
                      {:checked (if @hidden-cell false true)
                       :on-change (fn []
                                    (swap! hidden-cell not)
                                    (write-table-state! table-name @state-atom))
                       :c/root-class "w-auto"}]]]))
               col-model))]])]))))

(defn init-col-indexes
  [headers]
  (into [] (map-indexed (fn [i _] i) headers)))

(defn core-table
  "Reagent component for rendering the <table> with header/body."
  []
  (let [last-column-width (utils/ratom nil)]
    (fn [cfg col-model data state-atom]
      [:div {:class "relative"}
       [:table (assoc (:table cfg) :ref (fn [e] (when (and e (not @last-column-width))
                                                  (reset! last-column-width
                                                          (max (- (.-clientWidth (.-parentElement e))
                                                                  (.-clientWidth e))
                                                               200)))))
        [:thead {:class thead-class}
         (render-header-row col-model cfg state-atom last-column-width)]
        [:tbody (:tbody cfg)
         (render-all-rows data state-atom cfg)]]])))

(defn generate-cols
  [cols-data]
  (mapv
   (fn [{:keys [name width]}]
     (let [key (if (empty? name) "" (keyword name))]
       {:path   [key]
        :header name
        :key    key
        :width  width}))
   cols-data))

(defn view
  [props]
  (let [col-defs   (generate-cols (:columns props))
        row-data   (:rows props)
        table-name (or (:table-name props) "default")
        model-indexes (init-col-indexes col-defs)
        cfg        {:table           {:class (utils/class-names root-class (:class props))}
                    :table-state     (merge
                                      {:draggable (or (:draggable props) false)
                                       :col-index-to-model model-indexes}
                                      (some-> (read-table-state! table-name)
                                              (assoc :col-index-to-model model-indexes)))
                    :column-model    col-defs
                    :c/tooltip-style (:c/tooltip-style props)
                    :table-name      table-name
                    :on-row-click (:on-row-click props)}
        local-state (utils/ratom (:table-state cfg))]
    [:div {:class "w-full"}
     (when (:visibility-ctrl props)
       [:div {:class "absolute top-0 right-0 z-10 w-[45px] h-[48px] bg-white shadow-[-8px_0px_4px_0px_rgba(255,255,255,0.70)]"}
        [column-visibility-dropdown local-state col-defs table-name]])
     [core-table cfg col-defs row-data local-state]]))
