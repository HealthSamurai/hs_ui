(ns hs-ui.components.kvlist
  (:require [hs-ui.text]
            [hs-ui.utils]
            [hs-ui.components.button]))

(defn component
  [props]
  [:div {:class "flex"}
   [:div {:class "flex flex-col"}
    (for [item (:c/items props)] ^{:key (:key item)}
      (when (:slot/value item)
        [hs-ui.text/value {:class "block text-nowrap text-elements-assistive"} (:slot/key item)]))]
   [:div {:class "flex flex-col pl-[31px]"}
    (for [item (:c/items props)] ^{:key (:key item)}
      (when (:slot/value item)
        [:div {:class "flex group"}
         [hs-ui.text/value (hs-ui.utils/merge-props
                            (:class/value item)
                            {:class "block truncate overflow-hidden"
                             :title (:slot/value item)})
          (:slot/value item)]
         (when (:slot/copy? item)
           [hs-ui.components.button/xs {:class "ml-x1 invisible group-hover:visible overflow-visible"
                                        :on-click (fn [_] (hs-ui.utils/copy-to-clipboard (:slot/value item)))}
            "COPY"])]
        ))]])

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
