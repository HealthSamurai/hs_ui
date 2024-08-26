(ns hs-ui.utils
  #?(:cljs (:require [reagent.core])))

(defn merge-props
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props properties-a properties-b)
     :clj  nil))

(defn class-names
  [a b]
  #?(:cljs (reagent.core/class-names a b)
     :clj  nil))
