(ns hs-ui.organisms.header
  (:require [hs-ui.text]))

(defn component
  [props]
  [:div
   (into [hs-ui.text/section-header {}] (:slot/label props))
   (into [hs-ui.text/assistive {:class "pt-[6px] block [&_*]:txt-assistive [&_*]:text-elements-assistive"}] (:slot/desc props))])
