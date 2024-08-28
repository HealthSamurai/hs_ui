(ns hs-ui.core
  (:require [hs-ui.components.button]))

(def button hs-ui.components.button/component)

(defn button-primary  [props & children]
  (into
   [hs-ui.components.button/component (assoc props :variant "primary")]
   children))

(defn button-critical [props & children]
  (into
   [hs-ui.components.button/component (assoc props :variant "critical")]
   children))

(defn button-secondary [props & children]
  (into
   [hs-ui.components.button/component (assoc props :variant "secondary")]
   children))

(defn button-tertiary [props & children]
  (into
   [hs-ui.components.button/component (assoc props :variant "tertiary")]
   children))
