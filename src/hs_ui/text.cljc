(ns hs-ui.text
  (:require [hs-ui.utils :as utils]))

(defn label
  [props & children]
  (into
   [:label (utils/merge-props {:class "inline-block font-medium"} props)]
   children))

(defn assistive
  [props & children]
  (into
   [:span (utils/merge-props {:class "inline-block text-assistive font-normal text-[theme(colors.color-elements-assistive)]"} props)]
   children))
