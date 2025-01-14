(ns hs-ui.elements.table
  (:require
    [reagent.core :as r :refer [atom]]
    [reagent.dom :as rdom]
    [clojure.string :as str]
    [goog.events :as events]
    [goog.i18n.NumberFormat.Format]
    [hs-ui.utils :as u]
    [hs-ui.components.tooltip])
  (:import
    [goog.events EventType]))

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
   "whitespace-nowrap"
   ])

(def body-tr-class
  ["even:bg-[var(--color-surface-1)]"
   "aria-selected:bg-[var(--color-surface-selected)]"
   "data-[role=link]:cursor-pointer"
   "data-[role=link]:hover:text-gray-400"
   "data-[role=link]:hover:bg-[var(--color-surface-1)]"])

(defn handle-drag-move
  "Attach a drag-move handler that receives mouse X and Y positions."
  [on-move]
  (fn [e]
    (.preventDefault e)
    (on-move (.-clientX e) (.-clientY e))))

(defn handle-drag-end
  "Cleans up listeners when dragging has finished."
  [move-fn end-atom]
  (fn [e]
    (events/unlisten js/window EventType.MOUSEMOVE move-fn)
    (events/unlisten js/window EventType.MOUSEUP @end-atom)))

(defn initiate-drag!
  "Sets up global listeners to track drag movement and end of drag."
  [on-move]
  (let [move-fn (handle-drag-move on-move)
        end-atom (atom nil)
        cleanup-fn (handle-drag-end move-fn end-atom)]
    (reset! end-atom cleanup-fn)
    (events/listen js/window EventType.MOUSEMOVE move-fn)
    (events/listen js/window EventType.MOUSEUP cleanup-fn)))

(defn deep-merge
  [a b]
  (if (and (map? a) (map? b))
    (merge-with deep-merge a b)
    b))

(defn extract-col-model
  "Given a state-atom and a col-index, returns the 'model' key in that position."
  [state-atom col-idx]
  (-> @state-atom :col-index-to-model (nth col-idx)))

(defn reorder-columns!
  "Swaps the column index in the state so drag-col is placed at drop-col."
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
  "A small handle that resizes the DOM node given by `cell-ref` when dragged."
  [cell-ref]
  [:span {:class        "inline-block w-2 absolute cursor-ew-resize h-full top-[30%] right-0 mr-[-12px] z-50"
          :on-click     #(.stopPropagation %)
          :on-mouse-down
          (fn [evt]
            (when-let [cell-node @cell-ref] 
              (let [init-x      (.-clientX evt)
                    init-width  (.-clientWidth cell-node)]
                (initiate-drag!
                             (fn [x _]
                               (aset cell-node "width" (- init-width (- init-x x)))))
                (.preventDefault evt))))}
   "|"])

(defn header-cell
  "Renders a <th> element, optionally draggable, with the provided column info."
  [col-info visible-idx model-idx cfg state-atom data-atom]
  (let [st           @state-atom
        hidden-cols  (:col-hidden st)
        draggable?   (:draggable st)
        col-model    (:column-model cfg)
        cell-ref (r/atom nil)
        ref-fn   (fn [el] (reset! cell-ref el))]
    [:th
     {:ref   ref-fn
      :class column-name-class
      :draggable    draggable?
      :on-drag-start (fn [e]
                       (-> (.-dataTransfer e) (.setData "text/plain" ""))
                       (swap! state-atom assoc :col-reordering true))
      :on-drag-over  #(swap! state-atom assoc :col-hover visible-idx)
      :on-drag-end   (fn [_]
                       (let [hovered-col (:col-hover @state-atom)]
                         (when (not= visible-idx hovered-col)
                           (reorder-columns! visible-idx hovered-col state-atom))
                         (swap! state-atom
                                assoc
                                :col-hover nil
                                :col-reordering nil)))
      :style        (merge
                     {:position "relative"
                      :cursor   (when draggable? "move")
                      :display  (when (get hidden-cols model-idx) "none")}
                     (when (and (:col-reordering st)
                                (= visible-idx (:col-hover st)))
                       {:border-right "6px solid #3366CC"}))}
     [:span {:style {:padding-right 50}}
      (:header col-info)]
     [resizer-handle cell-ref]]))

(defn render-header-row
  "Renders the table header row (i.e. <tr> containing <th>s)."
  [col-model cfg data-atom state-atom]
  [:tr
   (doall
     (map-indexed
       (fn [view-idx _]
         (let [model-idx (extract-col-model state-atom view-idx)
               info       (col-model model-idx)]
           ^{:key (or (:key info) model-idx)}
           [header-cell info view-idx model-idx cfg state-atom data-atom]))
       col-model))])

(defn resolve-cell-data
  "Finds cell data either via a ‘path’ or by applying an ‘expr’ function."
  [row cell-def]
  (let [{:keys [path expr]} cell-def]
    (or (and path (get-in row path))
        (and expr (expr row)))))

(defn render-data-row
  "Renders a single <tr> with its <td> cells for a given row."
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
                 value (resolve-cell-data row (model model-idx))]
             ^{:key (col-key row row-idx model-idx)}
             [:td
              {:class column-value-class
               :style {:border-right (when (and (:col-reordering st)
                                                (= visible-idx (:col-hover st)))
                                       "2px solid #3366CC")
                       :display (when (get hidden-map model-idx) "none")}}
              [hs-ui.components.tooltip/component
               {:place "top"
                :class (:c/tooltip-style cfg)
                :tooltip [:pre (or (:title value) (str (:value value)))]}
               (:value value)]]))
         (or model row)))]))

(defn render-all-rows
  "Maps each row in ‘rows’ to a <tr> (via render-data-row)."
  [rows state-atom cfg]
  (let [row-key-fn (or (:row-key cfg) (fn [_ i] i))]
    (doall
      (map-indexed
        (fn [i row] (render-data-row row i row-key-fn state-atom cfg))
        rows))))

(defn column-visibility-ctrl
  "Renders a list of checkboxes or toggles that show/hide columns."
  [state-atom col-model]
  (let [hidden-cols (r/cursor state-atom [:col-hidden])]
    [:ul {:class "flex"}
     (doall
       (map-indexed
         (fn [view-idx _]
           (let [model-idx  (extract-col-model state-atom view-idx)
                 info       (col-model model-idx)
                 hidden-cell (r/cursor hidden-cols [model-idx])]
             ^{:key (or (:key info) model-idx)}
             [:li
              {:style    {:margin 8
                          :cursor "pointer"}
               :on-click #(swap! hidden-cell not)}
              (:header info) " " (if @hidden-cell "☐" "☑")]))
         col-model))]))

(defn init-col-indices
  "Given a list of column definitions, store their initial [0..n] indexes in state."
  [headers]
  (into [] (map-indexed (fn [i _] i) headers)))

(defn core-table
  "Reagent class for rendering the main table element with header/body."
 []
  (fn [cfg col-model data state-atom]
    [:table (:table cfg)
     [:thead {:class thead-class}
      (render-header-row col-model cfg (r/atom data) state-atom)]
     [:tbody (:tbody cfg)
      (render-all-rows data state-atom cfg)]]))

(defn generate-cols
  "Generates a column model vector from a vector of maps with :name keys."
  [cols-data]
  (mapv
    (fn [{n :name}]
      {:path   [(keyword n)]
       :header n
       :key    (keyword n)})
    cols-data))

(defn view
  [props]
  (let [col-defs     (generate-cols (:columns props))
        row-data     (:rows props)
        cfg {:table           {:class (u/class-names root-class (:class props))}
             :table-state     {:draggable (if (false? (:draggable props)) false true)}
             :column-model    col-defs
             :c/tooltip-style (:c/tooltip-style props)}
        local-state (r/atom (:table-state cfg))]
    (swap! local-state assoc :col-index-to-model (init-col-indices col-defs))
    [:div {:class "w-max mt-2"}
     [:div
      [column-visibility-ctrl local-state col-defs]
      [core-table cfg col-defs row-data local-state]]]))
