(ns hs-ui.components.kvlist
  (:require [hs-ui.text]))

(defn component
  [props]
  [:table {:class "table-auto border-separate border-spacing-y-[6px]"}
   [:tbody
    (for [item (:c/items props)] ^{:key (:key item)}
      (when item
        [:tr
         [:td
          [hs-ui.text/value {:class "text-elements-assistive"} (:slot/key item)]]
         [:td {:class "pl-[31px]"}
          [hs-ui.text/value {} (:slot/value item)]]]))]])
