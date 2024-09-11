(ns hs-ui.core
  (:require
   [hs-ui.text]

   [hs-ui.components.button]
   [hs-ui.components.dropdown]
   [hs-ui.components.input]
   [hs-ui.components.checkbox]

   [hs-ui.organisms.checkbox]))

(def button   hs-ui.components.button/component)
(def dropdown hs-ui.components.dropdown/component)
(def input    hs-ui.components.input/component)
(def checkbox hs-ui.components.checkbox/component)

(def org-checkbox hs-ui.organisms.checkbox/component)
(def org-input    hs-ui.organisms.input/component)

(def text-page-header    hs-ui.text/page-header)
(def text-section-header hs-ui.text/section-header)
(def text-label          hs-ui.text/label)
(def text-assistive      hs-ui.text/assistive)

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
