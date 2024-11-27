(ns hs-ui.organisms.textarea
  (:require [hs-ui.components.textarea]
            [hs-ui.components.content-expand]
            [hs-ui.text]
            [hs-ui.utils]))

(defn component
  [props]
  [:div (hs-ui.utils/merge-props {:class "pb-3"} props)
   (when (:slot/label props)
     [hs-ui.text/label {:class "w-full pb-[11px]"} (:slot/label props)])
   [hs-ui.components.textarea/component props]
   (when (or (:slot/assistive props)
             (:slot/assistive-right props))
     [:div {:class "w-full flex gap-x1point5 justify-between pt-[12px]"}
      [hs-ui.text/assistive {:class (when (:data-invalid props) "text-critical-default")}
       (:slot/assistive props)]
      [:div (when (contains? props :c/expand?)
              [hs-ui.components.content-expand/component
               {:c/open?  (:c/expand? props)
                :class    (:c/expand-class props)
                :on-click (:c/on-expand props)}])]])
   ])