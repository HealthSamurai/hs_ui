(ns hs-ui.organisms.checkbox
  (:require [hs-ui.text]
            [hs-ui.components.checkbox]
            [hs-ui.layout]))

(defn component
  [props]
  [hs-ui.layout/control
   {:slot/control         [:div.flex.items-center
                           [hs-ui.components.checkbox/component props]
                           (if (:checked props)
                             [:span {:class "ml-[12px]"} "Enabled"]
                             [:span {:class "ml-[12px]"} "Disabled"])]
    :slot/label           (:label props)
    :slot/assistive       (:assistive props)
    :slot/assistive-right (when (contains? props :c/expand?)
                            [hs-ui.components.content-expand/component
                             {:c/open?  (:c/expand? props)
                              :class    (:c/expand-class props)
                              :on-click (:c/on-expand props)}])}])
