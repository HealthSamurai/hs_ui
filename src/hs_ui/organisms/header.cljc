(ns hs-ui.organisms.header
  (:require [hs-ui.text]
            [hs-ui.utils]))

(defn component
  [props]
  [:div (hs-ui.utils/merge-props {:class "pb-[12px]"} props)
   (into [hs-ui.text/page-header {}] (:slot/label props))
   (when (:slot/desc props)
     (into [hs-ui.text/assistive {:class "pt-[6px] block [&_*]:txt-assistive [&_*]:text-elements-assistive"}] (:slot/desc props)))])
