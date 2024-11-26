(ns hs-ui.components.kvlist
  (:require [hs-ui.text]
            [hs-ui.utils]
            [hs-ui.svg.copy]
            [hs-ui.svg.check-3]
            [hs-ui.components.button]))

(defn component
  [props]
  [:table.table-fixed
   [:tbody
    (for [item (:c/items props) :when (:value/value item)] ^{:key (:key/value item)}
      [:tr
       [:td
        [hs-ui.text/value {:class "text-elements-assistive text-nowrap"} (str (:key/value item))]]
       [:td.pl-2.group.flex
        [hs-ui.text/value {:class "truncate"} (str (:value/value item))]
        (when (or (:copy/copy item) (:copy/copy props))
          [hs-ui.components.button/xs {:class "ml-x1 invisible group-hover:visible overflow-visible"
                                       :on-click (fn [_] (hs-ui.utils/copy-to-clipboard (:value/value item)))}
           [:span {:class "group-focus:hidden"} hs-ui.svg.copy/svg]
           [:span {:class "hidden group-focus:block px-[2.7px]"} hs-ui.svg.check-3/svg]])]])]])

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
