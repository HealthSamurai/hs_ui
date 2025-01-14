(ns hs-ui.layout
  (:require [hs-ui.utils :as u]
            [hs-ui.text]
            #?(:cljs [reagent.core :as r])
            #?(:cljs [reagent.dom :as dom])
            [re-frame.core :as rf]))

(defn confirmation
  [props]
  [:div (u/merge-props {:class "border-t border-separator flex justify-end py-x2 px-[theme(spacing.x4)]"} props)
   (into [:div.space-x-x1] (:slot/right props))])

(defn nest
  [props]
  (let [show? (:c/show? props)]
    [:div (when show? {:class "outline outline-1 outline-separator rounded-corner-l"})
     [:div {:class ["px-x2" (if show? "py-x3" "pt-x3")]} (:slot/control props)]
     (when show?
       [confirmation {:slot/right (:slot/confirmation props) :class "px-[theme(spacing.x2)]"}])]))

(defn navbar
  [props]
  [:div (u/merge-props
         {:class "flex border-b border-separator items-center h-[64px] justify-between w-full px-x3"}
         props)
   [:div (:slot/left props)]
   [:div (:slot/middle props)]
   [:div (:slot/right props)]])

(defn control
  [props]
  [:div (u/merge-props {:class "pb-[12px]"} props)
   (when (:slot/label props)
     [hs-ui.text/label {:class "w-full pb-[11px]"} (:slot/label props)])
   (when (:c/assistive-top? props)
     [:div {:class "w-full flex justify-between pb-[12px]"}
      [hs-ui.text/assistive {:class (when (:data-invalid props) "text-critical-default")}
       (:slot/assistive props)]
      [:div (:slot/assistive-right props)]])
   (:slot/control props)
   (when-not (:c/assistive-top? props)
     (when (or (:slot/assistive props)
               (:slot/assistive-right props))
       [:div {:class "w-full flex gap-x1point5 justify-between pt-[12px]"}
        [hs-ui.text/assistive {:class (when (:data-invalid props) "text-critical-default")}
         (:slot/assistive props)]
        [:div (:slot/assistive-right props)]]))])

(defn expandeable-control
  [props]
  [:div
   (:slot/control props)
   [:div {:class ["w-full pb-x2" (if (:c/expand? props) "block" "hidden")]}
    (:slot/content props)]])

;;; Resizable views
(defn- react-ref
  ([]
   (react-ref nil))
  ([value]
   (let [ref (atom value)]
     #?(:clj  (reify
                clojure.lang.IFn (invoke [value] (reset! ref value))
                clojure.lang.IDeref (deref [_] @ref))
        :cljs (reify
                clojure.core/IFn (-invoke [_ value] (reset! ref value))
                clojure.core/IDeref (-deref [_] @ref))))))


(defn- vertical-resizer
  [{:keys [side panel-ref on-resize]}]
  (let [resizing (u/ratom false)
        initial-width (atom nil)
        initial-x (atom 0)]
    #?(:cljs
       (letfn [(mouse-move [e]
                 (when @resizing
                   (let [touch (if (= "touchmove" (.-type e)) (aget (.-touches e) 0) e)
                         delta-x (- (.. touch -clientX) @initial-x)
                         new-width ((case side :left - +) @initial-width delta-x)]
                     (set! (.. @panel-ref -style -width) (str new-width "px"))
                     (when on-resize (on-resize new-width)))))
               (mouse-up [e]
                 (reset! resizing false)
                 (set! js/document.body.style.cursor "auto")
                 (set! js/document.body.style.userSelect "auto")
                 (.removeEventListener js/document "mousemove" mouse-move)
                 (.removeEventListener js/document "mouseup" mouse-up)
                 (.removeEventListener js/document "touchmove" mouse-move)
                 (.removeEventListener js/document "touchend" mouse-up))
               (mouse-down [e]
                 (reset! resizing true)
                 (reset! initial-width (.-clientWidth @panel-ref))
                 (reset! initial-x (let [touch (if (= "touchstart" (.-type e)) (aget (.-touches e) 0) e)]
                                     (.. touch -clientX)))
                 (set! js/document.body.style.cursor "col-resize")
                 (set! js/document.body.style.userSelect "none")
                 (.addEventListener js/document "mousemove" mouse-move)
                 (.addEventListener js/document "mouseup" mouse-up)
                 (.addEventListener js/document "touchmove" mouse-move)
                 (.addEventListener js/document "touchend" mouse-up))]
         (fn [_]
           [:div {:class         ["flex items-center justify-center flex-shrink-0 flex-grow-0 w-[8px] -mx-1 cursor-col-resize z-10 relative touch-none"
                                  "before:content-[''] before:block before:h-full before:w-[4px] before:absolute before:left-[2px] hover:before:bg-gray-300"
                                  (when @resizing "before:bg-gray-300")]
                  :on-mouse-down mouse-down
                  :on-touch-start mouse-down}])))))



(defn- horizontal-resizer
  [{:keys [side panel-ref on-resize]}]
  (let [resizing (u/ratom false)
        initial-height (atom nil)
        initial-y (atom 0)]
    #?(:cljs
       (letfn [(mouse-move [e]
                 (when @resizing
                   (let [touch (if (= "touchmove" (.-type e)) (aget (.-touches e) 0) e)
                         delta-y (- (.. touch -clientY) @initial-y)
                         new-height ((case side :up - +) @initial-height delta-y)]
                     (set! (.. @panel-ref -style -height) (str new-height "px"))
                     (when on-resize (on-resize new-height)))))
               (mouse-up [e]
                 (reset! resizing false)
                 (set! js/document.body.style.cursor "auto")
                 (set! js/document.body.style.userSelect "auto")
                 (.removeEventListener js/document "mousemove" mouse-move)
                 (.removeEventListener js/document "mouseup" mouse-up)
                 (.removeEventListener js/document "touchmove" mouse-move)
                 (.removeEventListener js/document "touchend" mouse-up))
               (mouse-down [e]
                 (reset! resizing true)
                 (reset! initial-height (.-clientHeight @panel-ref))
                 (reset! initial-y (let [touch (if (= "touchstart" (.-type e)) (aget (.-touches e) 0) e)]
                                     (.. touch -clientY)))
                 (set! js/document.body.style.cursor "row-resize")
                 (set! js/document.body.style.userSelect "none")
                 (.addEventListener js/document "mousemove" mouse-move)
                 (.addEventListener js/document "mouseup" mouse-up)
                 (.addEventListener js/document "touchmove" mouse-move)
                 (.addEventListener js/document "touchend" mouse-up))]
         (fn [_]
           [:div {:class          ["flex items-center justify-center flex-shrink-0 flex-grow-0 h-[8px] -my-1 cursor-row-resize z-10 relative"
                                   "before:content-[''] before:block before:w-full before:h-[4px] before:absolute before:top-[2px] hover:before:bg-gray-300"
                                   "absolute top-0 right-0 z-100 bg-gray-900 w-5"
                                   (when @resizing "before:bg-gray-300")]
                  :on-mouse-down  mouse-down
                  :on-touch-start mouse-down}])))))


(defn- vertical-panel
  [{:keys [default-width]} & _]
  (let [internal-ref (react-ref)]
    (fn [{:keys [side class ref]} & children]
      [:<>
       (when (= :left side)
         [vertical-resizer {:side side :panel-ref internal-ref}])
       (into [:div {:class [class "flex flex-col overflow-y-auto"
                            (when side "flex-shrink-0")]
                    :style {:width default-width}
                    :ref   #(do (internal-ref %) (when ref (ref %)))}]
             children)
       (when (= :right side)
         [vertical-resizer {:side side :panel-ref internal-ref}])])))

(defn- v-separator
  [{:keys [class
           default-right-percent
           min-left-percent
           min-right-percent
           left-el-ref
           right-el-ref
           on-resize
           visible-on-hover]}]
  (let [resizing            (u/ratom false)
        initial-left-width  (atom nil)
        initial-right-width (atom nil)
        initial-x           (atom 0)
        resizer-ref         (react-ref)]
    #?(:cljs
       (letfn [(mouse-move [e]
                 (.preventDefault e)
                 (when @resizing
                   (let [touch             (if (= "touchmove" (.-type e))
                                             (aget (.-touches e) 0)
                                             e)
                         delta-x           (- (.. touch -clientX) @initial-x)
                         new-left-width    (+ @initial-left-width  delta-x)
                         new-right-width   (- @initial-right-width delta-x)
                         new-left-percent  (* (/ new-left-width (+ new-left-width new-right-width)) 100)
                         new-left-percent  (cond
                                             (< new-left-percent min-left-percent)
                                             min-left-percent

                                             (< (- 100 min-right-percent) new-left-percent)
                                             (- 100 min-right-percent)

                                             :else
                                             new-left-percent)

                         new-right-percent (* (/ new-right-width (+ new-left-width new-right-width)) 100)
                         new-right-percent (cond
                                             (< new-right-percent min-right-percent)
                                             min-right-percent

                                             (< (- 100 min-left-percent) new-right-percent)
                                             (- 100 min-left-percent)

                                             :else
                                             new-right-percent)]

                     (set! (.. @left-el-ref -style -width)  (str new-left-percent  "%"))
                     (set! (.. @right-el-ref -style -width) (str new-right-percent "%"))
                     (set! (.. @resizer-ref -style -right)  (str new-right-percent "%"))
                     ;; TODO
                     #_(when on-resize (on-resize new-right-width)))))
               (mouse-up [e]
                 (.preventDefault e)
                 (reset! resizing false)
                 (set! js/document.body.style.cursor "auto")
                 (set! js/document.body.style.userSelect "auto")
                 (.removeEventListener js/document "mousemove" mouse-move)
                 (.removeEventListener js/document "mouseup" mouse-up)
                 (.removeEventListener js/document "touchmove" mouse-move)
                 (.removeEventListener js/document "touchend" mouse-up))
               (mouse-down [e]
                 (.preventDefault e)
                 (reset! resizing true)
                 (reset! initial-left-width (.-clientWidth @left-el-ref))
                 (reset! initial-right-width (.-clientWidth @right-el-ref))
                 (reset! initial-x (let [touch (if (= "touchstart" (.-type e)) (aget (.-touches e) 0) e)]
                                     (.. touch -clientX)))
                 (set! js/document.body.style.cursor "col-resize")
                 (set! js/document.body.style.userSelect "none")
                 (.addEventListener js/document "mousemove" mouse-move)
                 (.addEventListener js/document "mouseup" mouse-up)
                 (.addEventListener js/document "touchmove" mouse-move)
                 (.addEventListener js/document "touchend" mouse-up))]
         ;; TODO If necessary to customize the design for separator, need to modify
         ;; split-view components, so that they will receive separator as DOM (hiccup).
         ;; In this component there should be a general design.
         (fn [_]
           [:<>
            [:style ".separator:hover .separator-line {border: 1px solid #bfc1c7}
                     .hover-separator:hover .separator {display: block;}"]
            [:div.separator {:class          (cond-> ["px-4 mx-[-1rem] z-[100] cursor-col-resize"]

                                               visible-on-hover
                                               (conj "opacity-0 hover:opacity-100 transition-opacity duration-300"))
                             :style          {:right (str default-right-percent "%")}
                             :ref            resizer-ref
                             :on-mouse-down  mouse-down
                             :on-touch-start mouse-down}
             [:div.separator-line {:class (into ["h-full w-0 absolute z-[100] [border: 1px solid #dbdde3]"]
                                                (if (vector? class) class [class]))
                                   :style (when @resizing {:border "1px solid #83868e"})}]]])))))

(defn- h-separator
  [{:keys [class
           default-lower-percent
           min-upper-percent
           min-lower-percent
           upper-el-ref
           lower-el-ref
           on-resize]}]
  (let [resizing             (u/ratom false)
        initial-upper-height (atom nil)
        initial-lower-height (atom nil)
        initial-y            (atom 0)
        resizer-ref          (react-ref)]
    #?(:cljs
       (letfn [(mouse-move [e]
                 (.preventDefault e)
                 (when @resizing
                   (let [touch             (if (= "touchmove" (.-type e))
                                             (aget (.-touches e) 0)
                                             e)
                         delta-y           (- (.. touch -clientY) @initial-y)
                         new-upper-height    (+ @initial-upper-height  delta-y)
                         new-lower-height   (- @initial-lower-height delta-y)
                         new-upper-percent  (* (/ new-upper-height (+ new-upper-height new-lower-height)) 100)
                         new-upper-percent  (cond
                                              (< new-upper-percent min-upper-percent)
                                              min-upper-percent

                                              (< (- 100 min-lower-percent) new-upper-percent)
                                              (- 100 min-lower-percent)

                                              :else
                                              new-upper-percent)

                         new-lower-percent (* (/ new-lower-height (+ new-upper-height new-lower-height)) 100)
                         new-lower-percent (cond
                                             (< new-lower-percent min-lower-percent)
                                             min-lower-percent

                                             (< (- 100 min-upper-percent) new-lower-percent)
                                             (- 100 min-upper-percent)

                                             :else
                                             new-lower-percent)]

                     (set! (.. @upper-el-ref -style -height)  (str new-upper-percent  "%"))
                     (set! (.. @lower-el-ref -style -height) (str new-lower-percent "%"))
                     (set! (.. @resizer-ref -style -right)  (str new-lower-percent "%"))
                     ;; TODO
                     #_(when on-resize (on-resize new-right-height)))))
               (mouse-up [e]
                 (.preventDefault e)
                 (reset! resizing false)
                 (set! js/document.body.style.cursor "auto")
                 (set! js/document.body.style.userSelect "auto")
                 (.removeEventListener js/document "mousemove" mouse-move)
                 (.removeEventListener js/document "mouseup" mouse-up)
                 (.removeEventListener js/document "touchmove" mouse-move)
                 (.removeEventListener js/document "touchend" mouse-up))
               (mouse-down [e]
                 (.preventDefault e)
                 (reset! resizing true)
                 (reset! initial-upper-height (.-clientHeight @upper-el-ref))
                 (reset! initial-lower-height (.-clientHeight @lower-el-ref))
                 (reset! initial-y (let [touch (if (= "touchstart" (.-type e)) (aget (.-touches e) 0) e)]
                                     (.. touch -clientY)))
                 (set! js/document.body.style.cursor "row-resize")
                 (set! js/document.body.style.userSelect "none")
                 (.addEventListener js/document "mousemove" mouse-move)
                 (.addEventListener js/document "mouseup" mouse-up)
                 (.addEventListener js/document "touchmove" mouse-move)
                 (.addEventListener js/document "touchend" mouse-up))]
         (fn [_]
           [:<>
            [:style ".separator:hover .separator-line {border: 1px solid #bfc1c7}"]
            [:div.separator {:class          "py-4 my-[-1rem] cursor-row-resize z-[200]"
                             :style          {:bottom (str default-lower-percent "%")}
                             :ref            resizer-ref
                             :on-mouse-down  mouse-down
                             :on-touch-start mouse-down}
             [:div.separator-line {:class (into ["w-full h-0 absolute z-[100] [border: 1px solid #dbdde3]"]
                                                (if (vector? class) class [class]))
                                   :style (when @resizing {:border "1px solid #83868e"})}]]])))))

(defn- assoc-prop-to-hiccup [hiccup prop-key prop-val]
  (let [el-name      (first hiccup)
        props        (if (map? (second hiccup)) (second hiccup) {})
        children-idx (if (map? (second hiccup)) 2 1)
        children     (drop children-idx hiccup)]
    (into [el-name (assoc props prop-key prop-val)] children)))

(defn- assoc-in-prop-to-hiccup [hiccup prop-key-path prop-val]
  (let [el-name      (first hiccup)
        props        (if (map? (second hiccup)) (second hiccup) {})
        children-idx (if (map? (second hiccup)) 2 1)
        children     (drop children-idx hiccup)]
    (into [el-name (assoc-in props prop-key-path prop-val)] children)))

(def vertical-root-class
  ["relative"
   "w-full"
   "flex"])

(def vertical-separator-class
  ["h-full"])

(defn vertical-split-view
  "Allowed keys in PROPS:
  :c/separator-class
  :c/default-left-percent
  :c/default-right-percent
  :c/min-left-percent
  :c/min-right-percent
  :c/visible-on-hover

  :class and the other regular properties/attributes are allowed too."
  [props left & [right]]
  (let [left-el-ref           (atom nil)
        right-el-ref          (atom nil)
        default-right-percent (if (:c/default-left-percent props)
                                (- 100 (:c/default-left-percent props))
                                (:c/default-right-percent props))]
    (fn [_ left & [right]]
      [:div (u/merge-props {:class vertical-root-class} props)
       (cond-> left
         :always                    (assoc-prop-to-hiccup :ref #(reset! left-el-ref %))
         (nil? right) (assoc-in-prop-to-hiccup [:style :width] "100%"))

       (when right
         [:<>
          [v-separator {:class             (u/class-names vertical-separator-class (:c/separator-class props))
                        :left-el-ref       left-el-ref
                        :right-el-ref      right-el-ref
                        :default-right-percent default-right-percent
                        :min-left-percent  (or (:c/min-left-percent props) 10)
                        :min-right-percent (or (:c/min-right-percent props) 10)
                        :visible-on-hover  (:c/visible-on-hover props)}]
          (assoc-prop-to-hiccup right :ref #(reset! right-el-ref %))])])))

(def horizontal-root-class
  ["relative"
   "h-full"
   "flex"
   "flex-col"])

(def horizontal-separator-class
  ["w-full"])

(defn horizontal-split-view
  "Allowed keys in PROPS:
  :c/separator-class
  :c/default-upper-percent
  :c/default-lower-percent
  :c/min-upper-percent
  :c/min-lower-percent
  :c/visible-on-hover

  :class and the other regular properties/attributes are allowed too."
  [props upper & [lower]]
  (let [upper-el-ref          (atom nil)
        lower-el-ref          (atom nil)
        default-lower-percent (if (:c/default-upper-percent props)
                                (- 100 (:c/default-upper-percent props))
                                (:c/default-lower-percent props))]
    (fn [_ upper & [lower]]
      [:div (u/merge-props {:class horizontal-root-class} props)
       (cond-> upper
         :always                 (assoc-prop-to-hiccup :ref #(reset! upper-el-ref %))
         (nil? lower) (assoc-in-prop-to-hiccup [:style :height] "100%"))

       (when lower
         [:<>
          [h-separator {:class             (u/class-names horizontal-separator-class (:c/separator-class props))
                        :upper-el-ref      upper-el-ref
                        :lower-el-ref      lower-el-ref
                        :default-lower-percent default-lower-percent
                        :min-upper-percent (or (:c/min-upper-percent props) 10)
                        :min-lower-percent (or (:c/min-lower-percent props) 10)}]
          (assoc-prop-to-hiccup lower :ref #(reset! lower-el-ref %))])])))
