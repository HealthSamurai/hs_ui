(ns hs-ui.elements.table
  (:require
   [clojure.string :as str]
   [hs-ui.utils :as utils]
   [hs-ui.components.tooltip]
   [hs-ui.components.list-item]
   [hs-ui.components.list-items]
   [hs-ui.organisms.checkbox]
   [hs-ui.svg.settings]
   [hs-ui.svg.trailing]
   #?(:cljs [reagent.core :as r])
   #?(:cljs [goog.events :as events]))
  #?(:cljs (:import [goog.events EventType])))

(def root-class
  ["table-fixed"
   "w-full"
   "border-spacing-0"
   "border-separate"])

(def thead-class
  ["group"
   "sticky"
   "top-0"
   "text-[var(--color-elements-assistive)]"])

(def column-name-class
  ["p-4"
   "font-medium"
   "text-nowrap"
   "text-left"
   "bg-[var(--color-surface-0)]"
   "border-b"
   "border-[var(--color-separator)]"
   "sticky"
   "top-0"])

(def table-cell-class
  ["px-3"
   "py-2"
   "border-x-[0.25rem]"
   "border-transparent"
   "whitespace-nowrap"
   "break-all"
   "text-ellipsis"
   "whitespace-nowrap"
   "group/cell"])

(def table-cell-border-class
  ["relative"
   "hover:shadow-[inset_0_0_0_1px_var(--color-cta)]"
   "transition-shadow"
   "delay-[400ms]"])

(def body-row-class
  ["even:bg-[var(--color-surface-1)]"
   "aria-selected:bg-[var(--color-surface-selected)]"
   "data-[role=link]:cursor-pointer"
   "data-[role=link]:hover:text-gray-400"
   "data-[role=link]:hover:bg-[rgba(34,120,225,0.10)]"
   "group/row"])

(def text-class
  ["truncate"
   "group-hover/row:opacity-40"
   "group-hover/cell:opacity-100"])

(def cell-toolbar-class
  ["absolute"
   "-top-[var(--spacing-x3)]"
   "left-0"
   "flex"
   "px-[6px]"
   "py-[var(--spacing-half)]"
   "gap-[6px]"
   "bg-[var(--color-cta)]"
   "rounded-t"
   "invisible"
   "group-hover/cell:visible"
   "transition-[visibility]"
   "ease-out"
   "delay-[400ms]"])

(def cell-toolbar-icon-class
  ["opacity-50"
   "text-[theme(colors.elements-readable-inv)]"
   "hover:opacity-100"])

(def local-store-key "hs-table-state")

(defn read-table-state!
  "Reads the entire table-state map from localStorage, returning a Clojure map or nil."
  [table-name]
  #?(:cljs
     (let [key (str local-store-key "-" table-name "-v3")]
       (when-let [json-str (js/localStorage.getItem key)]
         (try
           (js->clj (js/JSON.parse json-str) :keywordize-keys true)
           (catch :default _ nil))))
     :clj nil))

(defn write-table-state!
  "Writes the given portion of table-state (column widths, hidden columns, col order) to localStorage as JSON."
  [table-name new-state]
  #?(:cljs
     (let [key (str local-store-key "-" table-name "-v3")
           st  (select-keys new-state [:col-widths :col-hidden :col-index-to-model])]
       (js/localStorage.setItem key (js/JSON.stringify (clj->js st))))
     :clj nil))


(defn handle-drag-move
  [on-move]
  (fn [e]
    (.preventDefault e)
    (on-move (.-clientX e) (.-clientY e))))

(defn handle-drag-end
  [move-fn end-fn end-atom]
  (fn [e]
    (end-fn)
    #?(:cljs (events/unlisten js/window EventType.MOUSEMOVE move-fn))
    #?(:cljs (events/unlisten js/window EventType.MOUSEUP @end-atom))))

(defn initiate-drag!
  [on-move on-end]
  (let [move-fn (handle-drag-move on-move)
        end-atom (utils/ratom nil)]
    (reset! end-atom (handle-drag-end move-fn on-end end-atom))
    #?(:cljs (events/listen js/window EventType.MOUSEMOVE move-fn))
    #?(:cljs (events/listen js/window EventType.MOUSEUP @end-atom))))

(defn extract-col-model [state-atom col-idx]
  (let [col-index-to-model (:col-index-to-model @state-atom)]
    (nth col-index-to-model col-idx)))

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
   {:class        "absolute cursor-ew-resize top-[30%] right-[-10px] hidden text-[var(--color-separator)] hover:text-[var(--color-cta)] active:text-[var(--color-cta)] group-hover:inline-block w-6 z-[1]"
    :on-click     #(.stopPropagation %)
    :on-mouse-down
    (fn [evt]

      (when-let [cell-node @cell-ref]
        (let [init-x     (.-clientX evt)
              init-width (.-clientWidth cell-node)]
          (swap! state-atom assoc :col-resizing true)
          (initiate-drag!
           (fn [x _]
             (let [new-width (max 30 (- init-width (- init-x x)))]
               (swap! state-atom assoc-in
                      [:col-widths (keyword (str model-idx))] new-width)
               (write-table-state! table-name @state-atom)))
           (fn [_]
             (swap! state-atom assoc :col-resizing false)))
          (.preventDefault evt))))}
   "|"])

(def ^:private position-on-drag (utils/ratom {}))

(defn table-wider-than-vw?
  [table-name]
  #?(:cljs (when-let [el (-> js/document
                             (.querySelector (str "." table-name)))]
             (let [viewport-width (.-innerWidth js/window)
                   element-width  (.-offsetWidth el)
                   limit-width    (- viewport-width 332)]
               (> element-width limit-width)))
     :clj nil))

(defn header-cell
  [col-info visible-idx model-idx cfg state-atom last-child]
  (let [st           @state-atom
        hidden-cols  (:col-hidden st)
        draggable?   (:draggable st)
        table-name   (:table-name cfg)
        previous-visible-idx (get-in @position-on-drag [table-name :previous-visible-idx])
        cur-border-side (get-in @position-on-drag [table-name :border-side])
        col-width    (get-in st [:col-widths (keyword (str model-idx))])
        cell-ref     (utils/ratom nil)
        current-col-width (cond
                            col-width
                            (str col-width "px")

                            (:width col-info)
                            (:width col-info)

                            :else "200px")]

    [:th
     {:ref         (fn [el]
                     (reset! cell-ref el)
                     (when (and el last-child (not= 0 (.-clientWidth el)) (not (table-wider-than-vw? table-name)))
                       (set! (.-width (.-style el)) "100%")))
      :class       column-name-class
      :draggable   draggable?
      :on-drag-start (fn [e]
                       (set! (.. e -dataTransfer -effectAllowed) "move")
                       (-> (.-dataTransfer e) (.setData "text/plain" ""))
                       (swap! state-atom assoc
                              :col-reordering true
                              :dragging-column model-idx
                              :dragging-column-index visible-idx))
      :on-drag-end   (fn [_]
                       (let [hovered-col (:col-hover @state-atom)]
                         (when (and (not= visible-idx hovered-col)
                                    (or
                                     (and (= :border-left cur-border-side)
                                          (not= (:dragging-column-index st)
                                                (dec hovered-col)))
                                     (and (= :border-right cur-border-side)
                                          (not= (:dragging-column-index st)
                                                (inc hovered-col)))))
                           (reorder-columns! visible-idx hovered-col state-atom)
                           (write-table-state! (:table-name cfg) @state-atom))
                         (swap! state-atom assoc
                                :col-hover nil
                                :col-reordering nil
                                :dragging-column nil)
                         (swap! position-on-drag assoc-in [table-name :border-side] nil)))

      :style (cond-> {:position "relative"
                      :cursor   (cond
                                  (:col-reordering st)
                                  "grabbing"

                                  draggable?
                                  "grab")
                      :display  (when (get hidden-cols (keyword (str model-idx))) "none")
                      :width current-col-width}

               (= (:dragging-column st) model-idx)
               ;; TODO: fix color to var
               (assoc :background "rgba(113, 118, 132, 0.10)")

               (and (:col-reordering st)
                    (= visible-idx (:col-hover st))
                    (not= (:dragging-column st) model-idx)
                    cur-border-side)
               (merge
                (cond
                  (and (= :border-left cur-border-side)
                       (not= (:dragging-column-index st)
                             (dec visible-idx)))
                  {:border-left "0.25rem solid var(--color-elements-assistive)" :padding-left "0.75rem"}

                  (and (= :border-right cur-border-side)
                       (not= (:dragging-column-index st)
                             (inc visible-idx)))
                  {:border-right "0.25rem solid var(--color-elements-assistive)" :padding-right "0.75rem"})))}

      [:span {:class "block overflow-hidden"}
       (or (:header col-info) model-idx)]

     ;; This hide resize control during dragging prosses on cells with "dragging position stick" a.k.a. left or right border
     (when-not (and cur-border-side
                    (:col-reordering st)
                    (= visible-idx (if (= cur-border-side :border-right) (:col-hover st) (dec (:col-hover st))))
                    (not= (:dragging-column st) model-idx))
       [resizer-handle cell-ref model-idx state-atom (:table-name cfg)])]))

(defn last-column-index?
  [state-atom col-model index]
  (let [total-column-indexes (range (count col-model))
        visible-columns      (filter #(not (get (:col-hidden @state-atom) (keyword (str %))))
                                     total-column-indexes)]
    (= index (last visible-columns))))

(defn get-col-info [col-model model-idx]
  (-> (filter #(= model-idx (:header %)) col-model)
      (first)))

(defn render-header-row
  [col-model cfg state-atom]
  [:<>
   [:tr
    (doall
     (map-indexed
      (fn [view-idx _]
        (let [model-idx (extract-col-model state-atom view-idx)
              info       (get-col-info col-model model-idx)]
          ^{:key view-idx}
          [header-cell info view-idx model-idx cfg state-atom (last-column-index? state-atom col-model view-idx)]))
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

             (nil? value)
             true

             :else false)))

(defn cell-toolbar
  [icons]
  [:div {:class cell-toolbar-class}
   [:<>
    (map-indexed
     (fn [idx {:keys [svg on-click]}]
       ^{:key idx}
       [:span {:class cell-toolbar-icon-class :on-click on-click} svg])
     icons)]])

(defn render-data-row
  [row row-idx row-key-fn state-atom cfg]
  (let [st          @state-atom
        hidden-map  (:col-hidden st)
        col-key     (or (:col-key cfg) (fn [_ rn cn] cn))
        model       (:column-model cfg)
        on-row-click (:on-row-click cfg)]
    ^{:key (row-key-fn row row-idx)}
    [:tr {:class body-row-class
          :data-role     (when on-row-click "link")
          :on-click      (when on-row-click #(on-row-click row))
          :aria-selected (:selected? row)}
     (doall
      (map-indexed
       (fn [visible-idx _]
         (let [model-idx (extract-col-model state-atom visible-idx)
               value     (resolve-cell-data row (get-col-info model model-idx))
               table-name (:table-name cfg)
               previous-visible-idx (get-in @position-on-drag [table-name :previous-visible-idx])
               cur-border-side (get-in @position-on-drag [table-name :border-side])]

           ^{:key (col-key row row-idx model-idx)}
           [:td
            {:class (if (:cell-toolbar-active? cfg)
                      (concat table-cell-class table-cell-border-class)
                      table-cell-class)
             :style (let [style (cond-> {:display (when (get hidden-map (keyword (str model-idx))) "none")}

                                  (and (:col-reordering st)
                                       (= visible-idx (:col-hover st))
                                       (not= (:dragging-column st) model-idx)
                                       cur-border-side)
                                  (merge
                                   (cond
                                     (and (= :border-left cur-border-side)
                                          (not= (:dragging-column-index st)
                                                (dec visible-idx)))
                                     {:border-left "0.25rem solid var(--color-elements-assistive)"}

                                     (and (= :border-right cur-border-side)
                                          (not= (:dragging-column-index st)
                                                (inc visible-idx)))
                                     {:border-right "0.25rem solid var(--color-elements-assistive)"}))

                                  (= (:dragging-column st) model-idx)
                                  ;; TODO: fix color to var
                                  (assoc :background "rgba(113, 118, 132, 0.10)"))]
                      style)}
            (if (need-tooltip? (:value value))
              [hs-ui.components.tooltip/component
               {:class   (:c/tooltip-style cfg)
                :tooltip [:pre (or (:tooltip value) (str (:value value)))]}
               (:value value)]
              [:div {:class text-class} (or (:value value) "-")])
            [cell-toolbar
             (map
              (fn [icon]
                (update icon :on-click partial model-idx (:value value)))
              (:cell-toolbar-icons cfg))]]))
       (or model row)))]))

(defn render-all-rows
  [rows state-atom cfg]
  (let [row-key-fn (or (:row-key cfg) (fn [_ i] i))]
    (doall
     (map-indexed
      (fn [i row] (render-data-row row i row-key-fn state-atom cfg ))
      rows))))

(defn column-visibility-dropdown
  [state-atom col-model cfg]
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

    (fn [state-atom col-model cfg]
      (let [st @state-atom
            table-name (:table-name cfg)
            draggable? (:draggable @state-atom)
            hidden-cols #?(:cljs (r/cursor state-atom [:col-hidden])
                           :clj nil)]

        [:div {:class "relative inline-block w-full mt-2.5 dropdown-container"}
         [:div {:class "w-full flex justify-start"}
          [:div {:on-click #(swap! menu-open? not)
                 :class
                 (utils/class-names "text-[theme(colors.elements-assistive)] p-2 cursor-pointer flex content-center justify-center w-[32px] h-[32px] hover:rounded-[50%] hover:bg-[var(--color-separator)] hover:text-[var(--color-cta)]"

                                          (if @menu-open? "rounded-[50%] bg-[var(--color-separator)]" ""))}
           hs-ui.svg.settings/svg]]
         (when @menu-open?
           [:div {:class "absolute right-[16px] mt-2 bg-white border shadow-md p-2 z-10 rounded-md"}

            [hs-ui.components.list-items/component  {}
             (doall
              (map-indexed
               (fn [view-idx _]
                 (let [model-idx   (extract-col-model state-atom view-idx)
                       info        (get-col-info col-model model-idx)
                       hidden-cell #?(:cljs (r/cursor hidden-cols [(keyword (str model-idx))])
                                      :clj nil)
                       cur-border-side (get-in @position-on-drag [table-name :control-border-side])]

                   [hs-ui.components.list-item/component {:key view-idx
                                                          :on-click (fn []
                                                                      (swap! hidden-cell not)
                                                                      (write-table-state! table-name @state-atom))
                                                          :class (utils/class-names "group px-[8px]"
                                                                                    (if (:control-col-reordering st) "!cursor-grabbing" ""))
                                                          :draggable   draggable?
                                                          :style (let [need-border? (and (:control-col-reordering st)
                                                                                         (= view-idx (:control-col-hover st))
                                                                                         (not= (:control-dragging-column st) model-idx)
                                                                                         cur-border-side)]
                                                                   (cond-> {:border-top "0.25rem solid transparent"
                                                                            :border-bottom "0.25rem solid transparent"}
                                                                     (and need-border?
                                                                          (= :border-bottom cur-border-side)
                                                                          (not= (:control-dragging-column-index st)
                                                                                (dec view-idx)))
                                                                     (merge {:border-bottom "0.25rem solid var(--color-elements-assistive)"
                                                                             :border-radius 0
                                                                             :padding-bottom 0})

                                                                     (and need-border?
                                                                          (= :border-top cur-border-side)
                                                                          (not= (:control-dragging-column-index st)
                                                                                (inc view-idx)))
                                                                     (merge {:border-top "0.25rem solid var(--color-elements-assistive)"
                                                                             :border-radius 0
                                                                             :padding-top 0})))
                                                          :on-drag-start (fn [e]
                                                                           (set! (.. e -dataTransfer -effectAllowed) "move")
                                                                           (-> (.-dataTransfer e) (.setData "text/plain" ""))
                                                                           (swap! state-atom assoc
                                                                                  :control-col-reordering true
                                                                                  :control-dragging-column model-idx
                                                                                  :control-dragging-column-index view-idx))
                                                          :on-drag-over   (fn [e]
                                                                            (let [current-cursor-position (.-clientY e)
                                                                                  old-cursor-position (:control-cursor-position st)
                                                                                  border-side (when (not= current-cursor-position old-cursor-position)
                                                                                                (if (> current-cursor-position old-cursor-position)
                                                                                                  :border-bottom
                                                                                                  :border-top))]
                                                                              (when (< 6 (abs (- current-cursor-position old-cursor-position)))
                                                                                (swap! state-atom assoc :control-cursor-position (.-clientY e)))
                                                                              (when border-side
                                                                                (swap! position-on-drag #(assoc-in % [table-name :control-border-side] border-side)))))
                                                          :on-drag-enter  (fn [e]
                                                                            (swap! state-atom assoc :control-col-hover view-idx))
                                                          :on-drag-end   (fn [_]
                                                                           (let [hovered-col (:control-col-hover @state-atom)]
                                                                             (when (and (not= view-idx hovered-col)
                                                                                        (or
                                                                                         (and (= :border-bottom cur-border-side)
                                                                                              (not= (:control-dragging-column-index st)
                                                                                                    (dec hovered-col)))
                                                                                         (and (= :border-top cur-border-side)
                                                                                              (not= (:control-dragging-column-index st)
                                                                                                    (inc hovered-col)))))
                                                                               (reorder-columns! view-idx hovered-col state-atom)
                                                                               (write-table-state! (:table-name cfg) @state-atom))
                                                                             (swap! state-atom assoc
                                                                                    :control-col-hover nil
                                                                                    :control-col-reordering nil
                                                                                    :control-dragging-column nil)
                                                                             (swap! position-on-drag assoc-in [table-name :control-border-side] nil)))}

                    [:div {:class "flex w-full justify-between"}
                     [hs-ui.organisms.checkbox/component
                      {:checked (if @hidden-cell false true)
                       :on-change (fn []
                                    (swap! hidden-cell not)
                                    (write-table-state! table-name @state-atom))
                       :c/root-class "w-auto"
                       :c/small true}]
                     [:div {:class "mr-4 flex justify-start w-full"} (or (:header info) model-idx)]
                     [:div {:class "w-[23px] flex items-center"}
                      [:div {:class (utils/class-names "hidden group-hover:block cursor-grab"
                                                       (if (:control-col-reordering st) "text-[--color-cta] !cursor-grabbing" ""))}
                       hs-ui.svg.trailing/svg]]]]))
               col-model))]])]))))

(defn init-col-indexes
  [headers]
  (into [] (map (fn [header]
                  (or (:header header) "empty"))
                        headers)))
(defn core-table
  []
  (fn [cfg col-model data state-atom]
    (let [table-name (:table-name cfg)
          st         @state-atom]
      [:div {:class "relative"}
       [:table
        {:class         (utils/class-names (-> cfg :table :class) table-name)
         :on-drag-over (fn [e]
                         (.preventDefault e)
                         (let [st @state-atom
                               current-cursor-position (.-clientX e)
                               old-cursor-position    (:cursor-position st)
                               border-side (when (not= current-cursor-position old-cursor-position)
                                             (if (> current-cursor-position old-cursor-position)
                                               :border-right
                                               :border-left))
                               old-border-side (get-in @position-on-drag [table-name :border-side])]
                           (when (< 40 (abs (- current-cursor-position old-cursor-position)))
                             (swap! state-atom assoc :cursor-position current-cursor-position))
                           (when (and border-side (not= old-border-side border-side))
                             (swap! position-on-drag
                                    #(assoc-in % [table-name :border-side] border-side)))))

         :on-drag-enter (fn [e]
                          (let [cell (.-target e)
                                cell-index (.-cellIndex cell)]
                            (when (some? cell-index)
                              (swap! state-atom assoc :col-hover cell-index))))

         :on-drag-end (fn [_]
                        (let [hovered-col (:col-hover @state-atom)
                              visible-idx   (:dragging-column-index st)
                              cur-border-side    (get-in st [table-name :border-side])]
                          (when (and (not= visible-idx hovered-col)
                                     (or
                                      (and (= :border-left cur-border-side)
                                           (not= (:dragging-column-index st)
                                                 (dec hovered-col)))
                                      (and (= :border-right cur-border-side)
                                           (not= (:dragging-column-index st)
                                                 (inc hovered-col)))))
                            (reorder-columns! visible-idx hovered-col state-atom)
                            (write-table-state! (:table-name cfg) @state-atom))
                          (swap! state-atom assoc
                                 :col-hover nil
                                 :col-reordering nil
                                 :dragging-column nil)
                          (swap! position-on-drag assoc-in [table-name :border-side] nil)))}

        [:thead {:class thead-class}
         [:tr
          (doall
           (map-indexed
            (fn [view-idx _]
              (let [model-idx (extract-col-model state-atom view-idx)
                    info      (get-col-info col-model model-idx)]
                ^{:key view-idx}
                [header-cell
                 info
                 view-idx
                 model-idx
                 cfg
                 state-atom
                 (last-column-index? state-atom col-model view-idx)]))
            col-model))]]

        [:tbody
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

(defn merge-model-indexes [new from-ls]
  (->> (concat from-ls new)
       (reduce (fn [acc item]
                 (if (contains? (set acc) item)
                   acc
                   (conj acc item))) [])))

(defn view
  [props]
  (let [col-defs   (generate-cols (:columns props))
        row-data   (:rows props)
        table-name (or (:table-name props) "default")
        model-indexes-new (init-col-indexes col-defs)
        state-from-local-storage (read-table-state! table-name)
        model-indexes (merge-model-indexes model-indexes-new
                                           (or (:col-index-to-model state-from-local-storage) []))
        cfg        {:table           {:class (utils/class-names root-class (:class props))}
                    :table-state     (merge
                                      {:draggable (or (:draggable props) false)}
                                      (assoc state-from-local-storage :col-index-to-model model-indexes))
                    :column-model    col-defs
                    :c/tooltip-style (:c/tooltip-style props)
                    :table-name      table-name
                    :on-row-click (:on-row-click props)
                    :cell-toolbar-active? (seq (:cell-toolbar-icons props))
                    :cell-toolbar-icons (:cell-toolbar-icons props)}
        local-state (utils/ratom (:table-state cfg))]
    [:div {:class "w-full"}
     (when (:visibility-ctrl props)
       [:div {:class "absolute top-0 right-0 z-10 w-[45px] h-[48px] bg-white shadow-[-8px_0px_4px_0px_rgba(255,255,255,0.70)]"}
        [column-visibility-dropdown local-state col-defs cfg]])
     [core-table cfg col-defs row-data local-state]]))
