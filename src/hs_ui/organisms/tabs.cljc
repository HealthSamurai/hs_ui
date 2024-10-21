(ns hs-ui.organisms.tabs
  (:require [hs-ui.components.tab]))

(def root-class
  ["flex"
   "space-x-[theme(spacing.x3)]"])

(defn component
  [props]
  [:div {:class root-class}
   (for [item (:c/items props)] ^{:key (:id item)}
     [hs-ui.components.tab/component (assoc item :name (:name props))])])
