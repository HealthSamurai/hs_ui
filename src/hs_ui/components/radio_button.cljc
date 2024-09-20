(ns hs-ui.components.radio-button
  (:require [hs-ui.utils :as utils]
            [hs-ui.text]
            [hs-ui.components.radio :as radio]))

(def root-class
  ["flex"
   "space-x-[theme(spacing.x1point5)]"])

(defn component
  [props]
  [radio/component (assoc props :class root-class)
   [:div {:class "-mt-[4px]"}
    [hs-ui.text/value {:class "text-elements-readable"} "Latest" #_(:data-label props)]
    [hs-ui.text/assistive {:class "block text-elements-assistive"} "Newest features, bug fixes, and optimizations, QA Passed." #_(:data-label props)]]])
