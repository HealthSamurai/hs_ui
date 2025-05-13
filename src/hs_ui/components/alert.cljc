(ns hs-ui.components.alert
  (:require [hs-ui.utils :as utils]
            [hs-ui.svg.error]
            [hs-ui.svg.warning-light]
            [hs-ui.svg.info]))

(def root-class
  ["flex"
   "rounded-[theme(borderRadius.corner-m)]"])

(def error-class
  ["p-[theme(spacing.x2)]"
   "bg-[theme(colors.surface-alert)]"
   "text-[theme(colors.critical-default)]"
   ])

(def warning-class
  ["p-[theme(spacing.x2)]"
   "bg-[theme(colors.surface-warning)]"
   "text-[theme(colors.elements-warning)]"
   ])

(def warning-message-class
  ["text-[#8F4E00]"
   "flex"
   "items-center"])

(def info-class
  ["p-[theme(spacing.x2)]"
   "bg-[theme(colors.surface-info)]"
   "text-[theme(colors.cta)]"])

(def info-message-class
  ["text-[theme(colors.cta)]"])

(def error-message-class
  ["text-[theme(colors.critical-default)]"])

(defn component
  [props & children]
  [:div (utils/merge-props {:class (cond-> root-class
                                     (= "error" (:severity props))
                                     (utils/class-names error-class)
                                     (= "error-message" (:severity props))
                                     (utils/class-names error-message-class)
                                     (= "warning" (:severity props))
                                     (utils/class-names warning-class)
                                     (= "warning-message" (:severity props))
                                     (utils/class-names warning-message-class)
                                     (= "info" (:severity props))
                                     (utils/class-names info-class)
                                     (= "info-message" (:severity props))
                                     (utils/class-names info-message-class))}
                           props)
   (some->>
    (cond
      (= "error" (:severity props)) hs-ui.svg.error/svg
      (= "error-message" (:severity props)) hs-ui.svg.error/svg
      (= "warning" (:severity props)) hs-ui.svg.warning-light/svg
      (= "info" (:severity props)) hs-ui.svg.info/svg)
    (conj [:div {:class "mr-[theme(spacing.x1)]"}]))
   (into [:div] children)
   (some->>
    (cond
      (= "warning-message" (:severity props)) hs-ui.svg.warning-light/svg-16)
    (conj [:div {:class "ml-[theme(spacing.x1)]"}]))])
