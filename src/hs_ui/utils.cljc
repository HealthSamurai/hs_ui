(ns hs-ui.utils
  (:refer-clojure :exclude [key])
  #?(:cljs (:require ["tailwind-merge" :as tw-merge]
                     ["fuse.js" :as fuse]
                     [reagent.core])
     :clj (:require [clojure.string]
                    [cheshire.core])))

(defn encode-uri
  [value]
  (when (string? value)
    #?(:cljs (js/encodeURI value)
       :clj  (clojure.string/replace (java.net.URLEncoder/encode value "UTF-8")
                                     "+" "%20"))))

(defn decode-uri
  [value]
  (when value
    #?(:cljs (js/decodeURI value)
       :clj  (java.net.URLDecoder/decode value))))

(defn key
  [prefix x]
  (str prefix "_" (:key x (hash x))))


(defn edn->json-pretty
  [edn]
  #?(:cljs (js/JSON.stringify (clj->js edn) nil 2)
     :clj  (cheshire.core/generate-string edn {:pretty true})))

(defn json-string->edn
  [json-string]
  (when (seq json-string)
    #?(:cljs (try (js->clj (js/JSON.parse json-string)
                           :keywordize-keys true)
                  (catch js/Error e
                    (prn "error" ::json-string->edn)))
       :clj (cheshire.core/parse-string json-string keyword))))

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

(defn stop-propagation
  [event]
  #?(:cljs (.stopPropagation event)
     :clj  nil))

(defn get-element-by-id
  [id]
  #?(:cljs (js/document.getElementById id)
     :clj  nil))

(defn show-modal
  [id]
  #?(:cljs (.showModal (get-element-by-id id))
     :clj  nil))

(defn close-modal
  [id]
  #?(:cljs (.close (get-element-by-id id))
     :clj  nil))

(defn get-event-target
  [event]
  #?(:cljs (.. event -target)
     :clj  (-> @event :target)))

(defn target-value
  [event]
  #?(:cljs (.. event -target -value)
     :clj  (-> @event :target :value)))

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

(defn keep-slot-properties
  [slot-name properties]
  (let [slot-ns (name slot-name)]
    (reduce-kv
     (fn [acc k v]
       (cond-> acc
         (or (contains? #{slot-ns} (namespace k))
             (nil? v))
         (-> (assoc (keyword (name k)) v)
             (dissoc k))))
     properties
     properties)))

(defn remove-custom-properties
  [properties]
  (reduce-kv
   (fn [acc k v]
     (cond-> acc
       (or #_(contains? #{"c" "slot" "class"} (namespace k))
           (namespace k))
       (dissoc k)))
   properties
   properties))

(defn ratom
  [value]
  #?(:cljs (reagent.core/atom value)
     :clj  (atom value)))

(defn class-names
  "Merges tailwind classes. To merge variable it should be wrapped like this 'pt-[--var]'"
  [a b]
  #?(:cljs (if b
             (let [a (reagent.core/class-names a)
                   b (reagent.core/class-names b)
                   r (tw-merge/twMerge a b)]
               r)
             a)
     :clj  nil))

(defn merge-properties
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props
            (assoc properties-a :class
                   (class-names (:class properties-a)
                                (:class properties-b)))
            (dissoc properties-b :class))
     :clj  (merge properties-a properties-b)))

(defn merge-props
  [properties-a properties-b]
  #?(:cljs (reagent.core/merge-props
            (assoc (remove-custom-properties properties-a)
                   :class (class-names (:class properties-a)
                                       (:class properties-b)))
            (dissoc (remove-custom-properties properties-b) :class))
     :clj  (merge properties-a properties-b)))

(defn merge-slot
  [slot-name properties-a properties-b]
  #?(:cljs
     (let [res (reagent.core/merge-props
                (assoc (remove-custom-properties properties-a)
                       :class (class-names (:class properties-a)
                                           (:class (-> (keep-slot-properties slot-name properties-b)
                                                       (remove-custom-properties)))))
                (dissoc (-> (keep-slot-properties slot-name properties-b)
                            (remove-custom-properties))
                        :class))]
       res)
     :clj  nil))


(defn slot [slot-name props & content]
  [:<>
   (reduce
    (fn [acc element]
      (if (coll? element)
        (cond
          (= (count element) 1) ; Element without props and value
          (if-let [custom-value (:value (keep-slot-properties slot-name props))]
            (conj acc
                  [(first element) props custom-value])
            (conj acc
                  [(first element) props]))

          (= (count element) 2) ; Element with props
          (if-let [custom-value (:value (keep-slot-properties slot-name props))]
            (conj acc
                  [(first element)
                   (merge-slot slot-name (second element) props)
                   custom-value])
            (conj acc
                  [(first element)
                   (merge-slot slot-name (second element) props)]))

          (= (count element) 3) ; Element with props and value
          (conj acc
                [(first element)
                 (merge-slot slot-name (second element) props)
                 (last element)])

          :else (conj acc element))
        (conj acc element)))
    [:<>]
    content)])

(defn fuse-search
  [fuse-options items search-string & [row-result]]
  (let [search-string-parts (filter seq (clojure.string/split (str search-string) #"\ "))]
    #?(:cljs
       (cond->>
         (js->clj
          (.search (fuse. (clj->js items)
                          (clj->js
                           (merge
                            {:threshold 0.2
                             :minMatchCharLength 2
                             :ignoreLocation true}
                            fuse-options)))
                   (clj->js
                    (or (:custom-search-setting fuse-options)
                        (reduce
                         (fn [acc field]
                           (let [field-name (if (string? field) field (:name field))]
                             (update acc :$or (fnil conj [])
                                     {:$and (map (fn [part] {field-name part})
                                                 search-string-parts)})))
                         {} (->> fuse-options :keys)))))

          :keywordize-keys true)
         (not row-result)
         (mapv :item)))))

(defn set-storage-item
  [keyname value]
  #?(:cljs (js/localStorage.setItem keyname value)
     :clj  nil))

(defn get-storage-item
  [keyname]
  #?(:cljs (js/localStorage.getItem keyname)
     :clj  nil))

(defn remove-storage-item
  [keyname]
  #?(:cljs (js/localStorage.removeItem keyname)
     :clj  nil))
