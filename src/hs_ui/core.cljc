(ns hs-ui.core
  (:require
   [hs-ui.components.button]
   [hs-ui.components.dropdown]
   [hs-ui.components.input]))

(def button   hs-ui.components.button/component)
(def dropdown hs-ui.components.dropdown/component)
(def input    hs-ui.components.input/component)

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

(defn button-xs [props & children]
  (into
   [hs-ui.components.button/component (assoc props :variant "xs")]
   children))
