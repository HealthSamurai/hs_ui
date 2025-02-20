(ns hs-ui.components.kvlist
  (:require
   [hs-ui.components.button]
   [hs-ui.components.tooltip]
   [hs-ui.svg.check-3]
   [hs-ui.svg.copy]
   [hs-ui.text]
   [hs-ui.svg.info-light]
   [hs-ui.utils]))

(defn value-view
  [props item value]
  [:div.flex.group.items-center
   [:div.pl-2
    (when (and (or (:copy/copy item)
                   (:copy/copy props))
               (some? value))
      [hs-ui.components.button/xs {:class "w-[22px] h-[22px] flex justify-center items-center invisible group-hover:visible overflow-visible"
                                   :on-click (fn [_] (hs-ui.utils/copy-to-clipboard value))}
       [:span {:class "group-focus:hidden"} hs-ui.svg.copy/svg]
       [:span {:class "hidden group-focus:block"} hs-ui.svg.check-3/svg]])]])

(defn component
  "Props:
  :class
  :c/items

  Item props:
  :value/value
  :value/hint
  :key/value
  :copy/copy"
  [props]
  [:table {:class (hs-ui.utils/class-names "table-fixed" (:class props))}
   [:tbody
    (for [item (:c/items props) :when (or (:value/value item)
                                          (:value/title item))]
      ^{:key (str (:item/key item)
                  (:key/value item))}
      [:tr
       [:td {:class "align-top"}
        [hs-ui.text/value {:class "text-elements-assistive text-nowrap"} (str (:key/value item))]]
       [:td {:class "pl-2 group grid grid-cols-[auto_1fr] flex items-center"}
        [hs-ui.text/value {:class "truncate"} (or (:value/title item) (:value/value item))]
        [value-view props item (:value/value item)]]])]])
