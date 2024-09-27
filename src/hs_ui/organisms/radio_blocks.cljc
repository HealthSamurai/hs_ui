(ns hs-ui.organisms.radio-blocks
  (:require [hs-ui.components.radio-button]
            [hs-ui.layout]))

(defn component
  [props]
  (let [options (:c/options props)]
    [hs-ui.layout/control
     {:slot/control         [:div {:class "border border-border-default rounded-corner-m divide-y"}
                             (for [option options]
                               [hs-ui.components.radio-button/component
                                (merge
                                 {:class "p-x2 pt-[19px]"
                                  :disabled (:disabled props)}
                                 option)])]
      :slot/label           (:slot/label props)
      :c/assistive-top?     true
      :slot/assistive       (:slot/assistive props)
      :slot/assistive-right (when (contains? props :c/expand?)
                              [hs-ui.components.content-expand/component
                               {:c/open?  (:c/expand? props)
                                :class    (:c/expand-class props)
                                :on-click (:c/on-expand props)}])}]))
