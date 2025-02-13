(ns hs-ui.organisms.checkbox
  (:require [hs-ui.components.checkbox]
            [hs-ui.components.content-expand]
            [hs-ui.components.tooltip]
            [hs-ui.layout]
            [hs-ui.text]
            [hs-ui.utils]))

(defn component
  [props]
  [:div {:class (hs-ui.utils/class-names "flex w-full" (:c/root-class props))}
   (let [tooltip-props (:c/checkbox-tooltip props)
         checkbox [hs-ui.components.checkbox/component props]]
     (if tooltip-props
       [hs-ui.components.tooltip/component tooltip-props checkbox]
       checkbox))

   [hs-ui.text/label {:class "pl-[12px] pb-[12px] w-full"} (:label props)
    [:div {:class "w-full flex justify-between space-x-2 pt-[2px]"}
     [hs-ui.text/assistive {:class "block w-full"} (:assistive props)]
     (when (contains? props :c/expand?)
       [:div
        [hs-ui.components.content-expand/component
         {:c/open?  (:c/expand? props)
          :class    (:c/expand-class props)
          :on-click (:c/on-expand props)}]])]]])
