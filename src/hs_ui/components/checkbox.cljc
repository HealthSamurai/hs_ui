(ns hs-ui.components.checkbox
  (:require [hs-ui.svg.lock]))

;; TODO: Merge props, migrate styles to tailwind
(defn component
  [props]
  [:label.toggle.block
   [:input {:type      "checkbox"
            :checked   (:checked props)
            :disabled  (:disabled props)
            :on-change (fn [event]
                         (when-let [on-change-fn (:on-change props)]
                           (on-change-fn event (.. event -target -checked))))}]
   [:div.slide-toggle
    [:div.text-elements-assistive.lock-icon hs-ui.svg.lock/svg]]
   ])
