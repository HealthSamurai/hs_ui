(ns hs-ui.components.kvlist
  (:require [hs-ui.text]))

(defn component
  [props]
  [:table {:class "table-auto py-x1 px-x1point5"}
   [:tbody
    (for [item (:c/items props)] ^{:key (:key item)}
      [:tr
       [:td {:class "pt-x1"}
        [hs-ui.text/value {:class "text-elements-assistive"} (:slot/key item)]]
       [:td {:class "pt-x1 pl-[31px]"}
        [hs-ui.text/value {} (:slot/value item)]]])]])
