(ns hs-ui.organisms.radio-blocks
  (:require [hs-ui.components.content-expand]
            [hs-ui.components.radio-button]
            [hs-ui.components.tooltip]
            [hs-ui.layout]))

(defn component
  [props]
  (let [options (:c/options props)]
    [hs-ui.layout/control
     {:class                (:c/root-layout-class props)
      :slot/control         [:div {:class "border border-border-default rounded-corner-m divide-y"}
                             (for [option options
                                   :let [tooltip-props (:c/radio-tooltip props)
                                         radio-button
                                         ^{:key (hash option)}
                                         [hs-ui.components.radio-button/component
                                          (merge
                                           {:class "p-x2 pt-[19px]"
                                            :disabled (:disabled props)}
                                           option)]]]

                               (if tooltip-props
                                 ^{:key (str (hash option) "-with-tooltip")}
                                 [hs-ui.components.tooltip/component tooltip-props radio-button]
                                 radio-button))]
      :slot/label           (:slot/label props)
      :c/assistive-top?     true
      :slot/assistive       (:slot/assistive props)
      :slot/assistive-right (when (contains? props :c/expand?)
                              [hs-ui.components.content-expand/component
                               {:c/open?  (:c/expand? props)
                                :class    (:c/expand-class props)
                                :on-click (:c/on-expand props)}])}]))
