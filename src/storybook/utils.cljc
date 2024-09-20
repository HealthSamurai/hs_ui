(ns storybook.utils
  #?(:cljs (:require [reagent.core])))

;; html.light html.dark

(defn reagent-reactify-component
  [component]
  #?(:cljs (reagent.core/reactify-component component)
     :clj  component))

(defn reagent-as-element
  [component]
  #?(:cljs (reagent.core/as-element component)
     :clj  component))

(defn reagent-create-class
  [component]
  #?(:cljs (reagent.core/create-class component)
     :clj  component))

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  value))
