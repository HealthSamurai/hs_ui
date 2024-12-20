(ns hs-ui.organisms.tabs
  (:require
   [hs-ui.utils :as u]
   [hs-ui.components.tab]))

(def root-class
  ["flex"
   "space-x-[theme(spacing.x3)]"])

(defn component
  "Accepts a vector of maps, each map might have
  :id
  :name
  :c/selected?
  :checked
  :on-change
  :slot/content"
  [props]
  [:div (u/merge-props {:class root-class} props)
   (for [item (:c/items props)] ^{:key (:id item)}
     [hs-ui.components.tab/component (assoc item :name (:name props))])])
