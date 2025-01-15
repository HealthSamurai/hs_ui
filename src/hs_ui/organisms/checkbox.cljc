(ns hs-ui.organisms.checkbox
  (:require [hs-ui.text]
            [hs-ui.components.checkbox]
            [hs-ui.components.content-expand]
            [hs-ui.utils]
            [hs-ui.layout]))

(defn component
  [props]
  [:div {:class (hs-ui.utils/class-names "flex w-full" (:c/root-class props))}
   [hs-ui.components.checkbox/component props]
   [hs-ui.text/label {:class "pl-[12px] pb-[12px] w-full"} (:label props)
    [:div {:class "w-full flex justify-between space-x-2 pt-[2px]"}
     [hs-ui.text/assistive {:class "block w-full"} (:assistive props)]
     (when (contains? props :c/expand?)
       [:div 
        [hs-ui.components.content-expand/component
         {:c/open?  (:c/expand? props)
          :class    (:c/expand-class props)
          :on-click (:c/on-expand props)}]])]]])
