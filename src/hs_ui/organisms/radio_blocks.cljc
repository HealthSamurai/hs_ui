(ns hs-ui.organisms.radio-blocks
  (:require [hs-ui.components.radio-button]))

(defn component
  [props]
  (let [options (:c/options props)]
    [:<>
     [hs-ui.text/label {:class "pb-[8px]"} (:slot/label props)]
     [hs-ui.text/assistive {:class "block pb-[12px]"} (:slot/assistive props)]
     [:div {:class "border border-border-default rounded-corner-m divide-y"}
      (for [option options]
        [hs-ui.components.radio-button/component
         (merge
          {:class "p-x2 pt-[19px]"
           :disabled (:disabled props)}
          option)])]]))
