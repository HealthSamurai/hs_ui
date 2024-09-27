(ns hs-ui.utils
  #?(:cljs (:require ["tailwind-merge" :as tw-merge]
                     [reagent.core])))

(defn remove-custom-properties
  [properties]
  (reduce-kv
   (fn [acc k v]
     (cond-> acc
       (or (contains? #{"c" "slot"} (namespace k))
           (nil? v))
       (dissoc k)))
   properties
   properties))

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  nil))

(defn class-names
  "Merges tailwind classes. To merge variable it should be wrapped like this 'pt-[--var]'"
  [a b]
  #?(:cljs (let [a (reagent.core/class-names a)
                 b (reagent.core/class-names b)
                 r (tw-merge/twMerge a b)]
             r)
     :clj  nil))


(defn merge-props
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props
            (assoc (remove-custom-properties properties-a)
                   :class (class-names (:class properties-a)
                                       (:class properties-b)))
            (dissoc (remove-custom-properties properties-b) :class))
     :clj  nil))
