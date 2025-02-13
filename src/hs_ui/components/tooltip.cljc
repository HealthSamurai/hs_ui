(ns hs-ui.components.tooltip
  (:require
   #?(:cljs [reagent.core :as r])
   [hs-ui.utils :as utils]))

(def ^:private margin-top 16)
(def ^:private margin-left 16)

(defn- clamp-tooltip-top
  [raw-top tooltip-height doc-height]
  (let [top1    (max raw-top margin-top)
        t-bottom (+ top1 tooltip-height)]
    (if (<= t-bottom doc-height)
      top1
      (max margin-top (- doc-height tooltip-height)))))

(defn component
  []
  (let [show?             (utils/ratom false)
        parent-ref        (utils/ratom nil)
        tooltip-ref       (utils/ratom nil)
        tooltip-pos       (utils/ratom nil)
        tooltip-placement (utils/ratom nil)
        timeout-id        (utils/ratom nil)]

    (fn [{:keys [tooltip class error?]
          :or   {class ""}}
         & content]

      (letfn [(update-position! []
                #?(:cljs (when (and @show? @parent-ref @tooltip-ref)
                           (let [parent-rect  (.getBoundingClientRect @parent-ref)
                                 tooltip-rect (.getBoundingClientRect @tooltip-ref)

                                 ;; Viewport dimensions
                                 doc-width  (.-innerWidth js/window)
                                 doc-height (.-innerHeight js/window)

                                 ;; Parent geometry
                                 p-top      (.-top parent-rect)
                                 p-bottom   (.-bottom parent-rect)
                                 p-left     (.-left parent-rect)
                                 p-right    (.-right parent-rect)
                                 p-width    (.-width parent-rect)
                                 p-height   (.-height parent-rect)
                                 p-center-x (+ p-left (/ p-width 2))
                                 p-center-y (+ p-top  (/ p-height 2))

                                 ;; Tooltip geometry
                                 t-width    (.-width tooltip-rect)
                                 t-height   (.-height tooltip-rect)

                                 ;; Offsets
                                 offset        8
                                 min-left-dist  300
                                 min-right-dist 300

                                 ;; Decide if it's "big" according to your own logic/threshold
                                 big-threshold 300
                                 is-big?       (> t-height big-threshold)

                                 ;; If we haven't decided a placement yet, pick one now:
                                 chosen-placement
                                 (or @tooltip-placement
                                     (if is-big?
                                       (if (< p-center-x (/ doc-width 2))
                                         :right
                                         :left)

                                       (cond
                                         (< p-left min-left-dist)
                                         :right

                                         (> p-right (- doc-width min-right-dist))
                                         :left

                                         :else
                                         :top)))

                                 ;; Compute an (x,y) raw location
                                 [raw-left raw-top chosen-placement']
                                 (case chosen-placement
                                   :right
                                   (let [left (+ p-right offset)
                                         top  (- p-center-y (/ t-height 2))]
                                     [left top :right])

                                   :left
                                   (let [left (- p-left offset t-width)
                                         top  (- p-center-y (/ t-height 2))]
                                     [left top :left])

                                   :top
                                   (let [above-top       (- p-top offset t-height)
                                         below-top       (+ p-bottom offset)
                                         top             (if (neg? above-top)
                                                           below-top  ; flip to bottom if no space
                                                           above-top)
                                         left            (- p-center-x (/ t-width 2))
                                         final-placement (if (neg? above-top) :bottom :top)]
                                     [left top final-placement])

                                   (let [below-top (+ p-bottom offset)
                                         left      (- p-center-x (/ t-width 2))]
                                     [left below-top :bottom]))

                                 final-left (-> raw-left
                                                (max 0)
                                                (min (- doc-width t-width)))

                                 final-top  (clamp-tooltip-top raw-top t-height doc-height)]

                             (reset! tooltip-placement chosen-placement')
                             (reset! tooltip-pos
                                     {:left (if (< final-left margin-left)
                                              margin-left
                                              final-left)
                                      :top  final-top})))
                   :clj nil))]

        (fn [{:keys [tooltip class error?] :or {class ""}} & content]
          [:div {:on-mouse-leave (fn [_]
                                   (reset! show? false))}


           [:div
            {:ref            #(reset! parent-ref %)
             :class          "truncate w-fit max-w-full"
             :on-mouse-enter (fn [_]
                               (let [id #?(:cljs (js/setTimeout (fn []
                                                                (reset! show? true)
                                                                (reset! tooltip-placement nil)
                                                                (r/after-render update-position!)) 300)
                                           :clj nil)]
                                 (reset! timeout-id id)))
             :on-mouse-leave (fn [_]
                               (when @timeout-id
                                 #?(:cljs (js/clearTimeout @timeout-id)
                                    :clj nil)))}

            (if (string? content)
              content
              (into [:<>] content))]

           (when @show?
             [:div
              {:ref   #(reset! tooltip-ref %)
               :class (hs-ui.utils/class-names
                       ["text-sm p-2 rounded shadow-xl z-[999]"
                        (if error?
                          "text-white bg-[var(--color-critical-default)]"
                          "text-[var(--color-elements-readable-inv)] bg-[var(--color-elements-assistive)]")]
                       class)
               :style (merge {:position "fixed"} @tooltip-pos)}
              tooltip])])))))
