(ns hs-ui.organisms.input
  (:require [hs-ui.text]
            [hs-ui.components.input]))


(defn component
  [props]
  [:<>
   [hs-ui.text/label {:class "pb-[11px]"} (:slot/label props)]
   [hs-ui.components.input/component props]
   [hs-ui.text/assistive {:class ["block pt-[12px]"
                                  (when (:data-invalid props) "text-critical-default")]}
    (:slot/assistive props)]])
