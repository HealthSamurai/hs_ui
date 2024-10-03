(ns hs-ui.utils
  #?(:cljs (:require ["tailwind-merge" :as tw-merge]
                     [reagent.core])))

(defn unsecured-copy-to-clipboard
  [text]
  #?(:cljs (let [textarea (js/document.createElement "textarea")]
             (set! (.-value textarea) text)
             (js/document.body.appendChild textarea)
             (.focus textarea)
             (.select textarea)
             (js/document.execCommand "copy")
             (js/document.body.removeChild textarea))
     :clj  (println "unsecured-copy-to-clipboard")))

(defn copy-to-clipboard
  [text]
  #?(:cljs (if js/window.isSecureContext
             (js/navigator.clipboard.writeText text)
             (unsecured-copy-to-clipboard text))
     :clj  (println "clipboard-write-text")))

(defn get-component-properties
  [arguments]
  (let [properties (first arguments)]
    (if (map? properties) properties {})))

(defn get-component-children
  [arguments]
  (if (empty? (get-component-properties arguments))
    (into [:<>] arguments)
    (when-let [children (seq (next arguments))]
      (into [:<>] children))))

(defn remove-custom-properties
  [properties]
  (reduce-kv
   (fn [acc k v]
     (cond-> acc
       (or (contains? #{"c" "slot" "class"} (namespace k))
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

(defn merge-properties
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props
            (assoc properties-a :class
                   (class-names (:class properties-a)
                                (:class properties-b)))
            (dissoc properties-b :class))
     :clj  nil))

(defn merge-props
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props
            (assoc (remove-custom-properties properties-a)
                   :class (class-names (:class properties-a)
                                       (:class properties-b)))
            (dissoc (remove-custom-properties properties-b) :class))
     :clj  nil))
