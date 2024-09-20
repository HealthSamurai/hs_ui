(ns hs-ui.components.alert
  (:require [hs-ui.utils :as utils]
            [hs-ui.svg.error]
            [hs-ui.svg.warning-light]
            [hs-ui.svg.info]))

(def root-class
  ["flex"
   "p-[theme(spacing.x2)]"
   "rounded-[theme(borderRadius.corner-m)]"])

(def error-class
  ["bg-[theme(colors.surface-alert)]"
   "text-[theme(colors.critical-default)]"
   ])

(def warning-class
  ["bg-[theme(colors.surface-warning)]"
   "text-[theme(colors.elements-warning)]"
   ])

(def info-class
  ["bg-[theme(colors.surface-info)]"
   "text-[theme(colors.cta)]"])

(defn component
  [props & children]
  [:div (utils/merge-props {:class (cond-> root-class
                                     (= "error" (:severity props))
                                     (utils/class-names error-class)
                                     (= "warning" (:severity props))
                                     (utils/class-names warning-class)
                                     (= "info" (:severity props))
                                     (utils/class-names info-class))}
                           props)
   [:div {:class "mr-[theme(spacing.x1)]"}
    (cond
      (= "error" (:severity props)) hs-ui.svg.error/svg
      (= "warning" (:severity props)) hs-ui.svg.warning-light/svg
      (= "info" (:severity props)) hs-ui.svg.info/svg)]
   (into [:div] children)
   [:div {:class "ml-[theme(spacing.x4)] pl-[16px]"}
    ]])
