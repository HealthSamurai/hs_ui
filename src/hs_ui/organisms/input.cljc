(ns hs-ui.organisms.input
  (:require [hs-ui.text]
            [hs-ui.layout]
            [hs-ui.components.content-expand]
            [hs-ui.components.input]))


(defn component
  [props]
  [hs-ui.layout/control
   {:slot/control         [hs-ui.components.input/component props]
    :slot/label           (:slot/label props)
    :slot/assistive       (:slot/assistive props)
    :slot/assistive-right (when (contains? props :c/expand?)
                            [hs-ui.components.content-expand/component
                             {:c/open?  (:c/expand? props)
                              :class    (:c/expand-class props)
                              :on-click (:c/on-expand props)}])
    :class (:c/control-class props)}])
