(ns hs-ui.components.kvlist
  (:require
   [hs-ui.components.button]
   [hs-ui.svg.check-3]
   [hs-ui.svg.copy]
   [hs-ui.text]
   [hs-ui.utils]))

(defn value-view
  [props item value]
  [:div.flex.group
   [hs-ui.text/value {:class "truncate"}
    [:span {:class "text-[theme(colors.elements-assistive)] pr-1"} (str (:value/hint item))]
    (str value)]
   [:div.pl-2
    (when (or (:copy/copy item)
              (:copy/copy props))
      [hs-ui.components.button/xs {:class "w-[22px] h-[22px] flex justify-center items-center invisible group-hover:visible overflow-visible"
                                   :on-click (fn [_] (hs-ui.utils/copy-to-clipboard value))}
       [:span {:class "group-focus:hidden"} hs-ui.svg.copy/svg]
       [:span {:class "hidden group-focus:block"} hs-ui.svg.check-3/svg]])]])

(defn component
  "Props:
  :c/items

  Item props:
  :value/value
  :value/hint
  :key/value
  :copy/copy"
  [props]
  [:table.table-fixed
   [:tbody
    (for [item (:c/items props) :when (:value/value item)]
      ^{:key (str (:item/key item)
                  (:key/value item))}
      [:tr
       [:td {:class "align-top"}
        [hs-ui.text/value {:class "text-elements-assistive text-nowrap"} (str (:key/value item))]]
       [:td {:class "pl-2"}
        (if (sequential? (:value/value item))
          (for [value (:value/value item)] ^{:key (hs-ui.utils/key "value" value)}
            [value-view props item value])
          [value-view props item (:value/value item)])]])]])
