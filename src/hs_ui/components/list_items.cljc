(ns hs-ui.components.list-items
  (:require [hs-ui.utils :as utils]))

(def root-class
  ["space-y-[2px]"])

(defn component
  [props & children]
  (into
   [:div (utils/merge-props {:class root-class} props)]
   children))
