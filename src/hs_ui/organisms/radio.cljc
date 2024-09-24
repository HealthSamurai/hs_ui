(ns hs-ui.organisms.radio
  (:require [hs-ui.components.radio]
            [hs-ui.utils]
            [hs-ui.text]))

(defn component
  [props]
  (let [options (:c/options props) horizontal? (<= (count options) 2)]
    [:<>
     [hs-ui.text/label {:class "pb-[15px]"} (:slot/label props)]
     [:div {:class (when horizontal? "flex")}
      (for [option options]
        [hs-ui.components.radio/component
         (hs-ui.utils/merge-props
          {:class ["flex items-center space-x-[12px] mb-[theme(spacing.x1point5)]"
                   (when horizontal? "mr-[theme(spacing.x4)]")]
           :disabled (:disabled props)}
          option)
         [hs-ui.text/value {} (:slot/label option)]])]
     [hs-ui.text/assistive {} (:slot/assistive props)]]))
