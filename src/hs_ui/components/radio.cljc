(ns hs-ui.components.radio
  (:require [hs-ui.utils :as utils]))

(def default-class
  ["w-[16px]"
   "h-[16px]"
   "min-w-[16px]"
   "min-h-[16px]"
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
  [props & children]
  [:<>
   [:input (utils/merge-props {:type "radio" :class "hidden"}
                              (dissoc props :class))]
   (into
    [:label {:for (:id props) :class (:class props)}
     [:div {:class (utils/class-names
                    default-class
                    (cond (and (:checked props)
                               (:disabled props))
                          disabled-class

                          (:checked props)
                          checked-class

                          :else unchecked-class))}]]
    children)])
