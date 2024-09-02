(ns hs-ui.utils
  #?(:cljs (:require ["tailwind-merge" :as tw-merge]
                     [reagent.core])))

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  nil))

(defn merge-props
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props properties-a properties-b)
     :clj  nil))

(defn class-names
  "Merges tailwind classes. To merge variable it should be wrapped like this 'pt-[--var]'"
  [a b]
  #?(:cljs (let [a (reagent.core/class-names a)
                 b (reagent.core/class-names b)
                 r (tw-merge/twMerge a b)]
             r)
     :clj  nil))
