(ns hs-ui.organisms.checkbox
  (:require [hs-ui.text]
            [hs-ui.components.checkbox]))

(defn component
  [props]
  [:<>
   [hs-ui.text/label {:class "pb-[14px]"} (:label props)]
   [:div.flex.items-center
    [hs-ui.components.checkbox/component props]
    (if (:checked props)
      [:span {:class "ml-[12px]"} "Enabled"]
      [:span {:class "ml-[12px]"} "Disabled"])]
   [hs-ui.text/assistive {:class "block pt-[12px]"} (:assistive props)]])
