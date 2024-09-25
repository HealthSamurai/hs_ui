(ns hs-ui.components.radio-button
  (:require [hs-ui.utils :as utils]
            [hs-ui.text]
            [hs-ui.components.radio :as radio]))

(def root-class
  ["flex"
   "space-x-[theme(spacing.x1point5)]"])

(defn component
  [props]
  [radio/component (assoc props :class (utils/class-names root-class (:class props)))
   [:div {:class "-mt-[4px]"}
    [hs-ui.text/value {:class "text-elements-readable"} (:slot/label props)]
    [hs-ui.text/assistive {:class [(if (:checked props) "text-elements-links" "text-elements-assistive") "block"]}
     (:slot/desc props)]]])
