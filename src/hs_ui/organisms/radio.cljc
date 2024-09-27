(ns hs-ui.organisms.radio
  (:require [hs-ui.components.radio]
            [hs-ui.utils]
            [hs-ui.layout]
            [hs-ui.text]))

(defn component
  [props]
  (let [options (:c/options props) horizontal? (<= (count options) 2)]
    [hs-ui.layout/control
     {:slot/control         [:div {:class (when horizontal? "flex")}
                             (for [option options] ^{:key (hash option)}
                               [hs-ui.components.radio/component
                                (hs-ui.utils/merge-props
                                 {:class ["flex items-center space-x-[12px] mb-[theme(spacing.x1point5)]"
                                          (when horizontal? "mr-[theme(spacing.x4)]")]
                                  :disabled (:disabled props)}
                                 option)
                                [hs-ui.text/value {} (:slot/label option)]])]
      :slot/label           (:slot/label props)
      :c/assistive-top?     true
      :slot/assistive       (:slot/assistive props)
      :slot/assistive-right (when (contains? props :c/expand?)
                              [hs-ui.components.content-expand/component
                               {:c/open?  (:c/expand? props)
                                :class    (:c/expand-class props)
                                :on-click (:c/on-expand props)}])}]))
