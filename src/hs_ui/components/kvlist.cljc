(ns hs-ui.components.kvlist
  (:require [hs-ui.text]
            [hs-ui.utils]
            [hs-ui.components.button]))

; NOTE: Here is exprimental kvlist the original one is commented below

(defn component
  [props]
  [:div {:class "flex"}
   [:div {:class "flex flex-col gap-y-[12px]"}
    (for [item (:c/items props)]
      (when (:value/value item)
        ^{:key (:key/value item)}
        [hs-ui.utils/slot :key item
         [hs-ui.text/value {:class "block text-nowrap text-elements-assistive"}]]))]
   [:div {:class "flex flex-col pl-[31px]  gap-y-[12px]"}
    (for [item (:c/items props)]
      (when (:value/value item)
        ^{:key (:value/value item)}
        [:div {:class "flex group"}
         [hs-ui.utils/slot :value item
          [hs-ui.text/value {:class "block truncate overflow-hidden"
                             :title (:value/value item)}]]
         (when (or (:copy/copy item) (:copy/copy props))
           [hs-ui.utils/slot :copy item
            [hs-ui.components.button/xs {:class "ml-x1 invisible group-hover:visible overflow-visible"
                                         :on-click (fn [_] (hs-ui.utils/copy-to-clipboard (:value/value item)))}
             "COPY"]])]
        ))]])

(defn wrapper
  [props hashmap]
  [:<>
   (if (map? hashmap)
     (let [hashmap (reduce-kv
                    (fn [acc k v]
                      (conj acc {:key/value k
                                 :value/value v}))
                    []
                    hashmap)]
       [component (assoc props :c/items hashmap)])
     [component props hashmap])])

#_(defn component
  [props]
  [:table {:class "table-auto border-separate border-spacing-y-[6px]"}
   [:tbody
    (for [item (:c/items props)] ^{:key (:key item)}
      (when (:slot/value item)
        [:tr
         [:td
          [hs-ui.text/value {:class "text-nowrap text-elements-assistive "} (:slot/key item)]]
         [:td {:class "pl-[31px] group text-nowrap truncate max-w-0 w-full"}
          [hs-ui.text/value {} (:slot/value item)]
          (when (:slot/copy? item)
           [hs-ui.components.button/xs {:class "ml-x1  group-hover:visible overflow-visible"} "COPY"])]
         
         ]))]])
