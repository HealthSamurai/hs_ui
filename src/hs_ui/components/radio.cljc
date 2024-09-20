(ns hs-ui.components.radio
  (:require [hs-ui.utils :as utils]))

(def default-class
  ["w-[16px]"
   "h-[16px]"
   "rounded-full"])

(def unchecked-class
  ["border"
   "border-[theme(colors.border-default)]"])

(def checked-class
  ["border-[5px]"
   "border-[theme(colors.cta)]"])

(def disabled-class
  ["border-[5px]"
   "border-[theme(colors.elements-assistive)]"])

(defn component
  [props]
  [:<>
   [:input (utils/merge-props {:type "radio" :class "hidden"} props)]
   [:label {:for (:id props)}
    [:div {:class (utils/class-names
                   default-class
                   (cond (and (:checked props)
                              (:disabled props))
                         disabled-class

                         (:checked props)
                         checked-class

                         :else unchecked-class))}]]])
