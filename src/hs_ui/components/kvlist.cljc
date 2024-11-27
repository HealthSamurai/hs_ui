(ns hs-ui.components.kvlist
  (:require
   [hs-ui.components.button]
   [hs-ui.svg.check-3]
   [hs-ui.svg.copy]
   [hs-ui.text]
   [hs-ui.utils]))

(defn component
  [props]
  [:table.table-fixed
   [:tbody
    (for [item (:c/items props) :when (:value/value item)]
      ^{:key (str (:item/key item)
                  (:key/value item))}
      [:tr
       [:td
        [hs-ui.text/value {:class "text-elements-assistive text-nowrap"} (str (:key/value item))]]
       [:td {:class "pl-2 group grid grid-cols-[auto_1fr]"}
        [hs-ui.text/value {:class "truncate"} (str (:value/value item))]
        [:div.pl-2
         (when (or (:copy/copy item) (:copy/copy props))
           [hs-ui.components.button/xs {:class "ml-x1 invisible group-hover:visible overflow-visible"
                                        :on-click (fn [_] (hs-ui.utils/copy-to-clipboard (:value/value item)))}
            [:span {:class "group-focus:hidden"} hs-ui.svg.copy/svg]
            [:span {:class "hidden group-focus:block px-[2.7px]"} hs-ui.svg.check-3/svg]])]]])]])
