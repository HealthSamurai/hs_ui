(ns sync-design-tokens
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def design-tokens-dir (io/file "./AidboxDesignTokens"))

(def css-template-file (io/file "./tailwind.template.css"))

(def resulting-css-file (io/file "./tailwind.css"))

(def tailwind-config-template-file (io/file "./tailwind.config.template.js"))

(def resulting-tailwind-config-file (io/file "./tailwind.config.js"))

(def all-design-tokens
  [{:global? true
    :theme "light"
    :file (io/file design-tokens-dir  "Global/ThemeLight.json")}
   {:global? false
    :theme "light"
    :file (io/file design-tokens-dir  "Alias/ThemeLight.json")}
   {:global? true
    :theme "dark"
    :file (io/file design-tokens-dir  "Global/ThemeDark.json")}
   {:global? false
    :theme "dark"
    :file (io/file design-tokens-dir  "Alias/ThemeDark.json")}])

(defn read-design-tokens!
  [^java.io.File f]
  (if (.exists f)
    (json/parse-stream (io/reader f))
    (do
      (println :read-design-token/file-not-exists) (throw (ex-info (format "Provided design tokens file %s doesn't exist." f) {})))))

(defn traverse-tree [f acc root]
  (letfn [(walker [acc x path]
            (cond
              (map? x) (reduce-kv (fn [a k v] (walker a v (conj path k))) (f acc x path) x)
              (coll? x) (reduce (fn [a v] (walker a v (conj path (count path)))) (f acc x path) x)
              :else (f acc x path)))]
    (walker acc root [])))

(defn wrap-with-var-call
  [v]
  (format "var(%s)" v))

(defn path->css-var-name
  [path]
  (format "--%s" (str/join "-" path)))

(defn hide-value-under-var-call
  [path]
  (-> (path->css-var-name path)
      wrap-with-var-call))

(defn is-template-value? [v]
  (boolean (re-matches #"\{.*\}" v)))

(defn demoustache [v]
  (subs v 1 (dec (count v))))

(defn template-path->css-var-name [v]
  (format "--%s" (str/replace v "." "-")))

(defn eval-template-value [v]
  (if (is-template-value? v)
    (-> v
        demoustache
        template-path->css-var-name
        wrap-with-var-call)
    v))

(defn coerce-token-value-by-type
  [value type path alias?]
  (cond
    (and alias? (= type "color"))
    (eval-template-value value)

    (= type "color")
    value

    (and alias? (= type "dimension"))
    (eval-template-value value)

    (= type "dimension")
    value))

(defn build-css-vars-from-design-tokens
  [{:as design-tokens-meta, :keys [global? file]}]
  (let [alias? (not global?)]
    (traverse-tree
     (fn [acc node path]
       (if (and (map? node)
                (= #{"$value" "$type"} (set (keys node))))
         (let [node-value (get node "$value")
               node-type (get node "$type")]
           (conj acc (format "--%s: %s"
                             (str/join "-" path)
                             (coerce-token-value-by-type node-value node-type path alias?))))
         acc))
     []
     (read-design-tokens! file))))

(defn figma-exported-design-tokens->css-vars
  [design-tokens-list]
  (mapv
   (fn [design-tokens-meta]
     (assoc design-tokens-meta
            :css-vars
            (build-css-vars-from-design-tokens design-tokens-meta)))
   design-tokens-list))

(defn deep-merge [a b]
  (loop [[[k v :as i] & ks] b
         acc a]
    (if (nil? i)
      acc
      (let [av (get a k)]
        (if (= v av)
          (recur ks acc)
          (recur ks (if (and (map? v) (map? av))
                      (assoc acc k (deep-merge av v))
                      (assoc acc k v))))))))

(def design-tokens<>tailwind-configs
  {"color" "colors"
   "spacing" "spacing"
   "corner" "borderRadius"})

(defn build-tailwind-config-from-design-tokens
  [{:as design-tokens-meta, :keys [global? file]}]
  (let [alias? (not global?)]
    (traverse-tree
     (fn [acc node path]
       (if (and (map? node) (= #{"$value" "$type"} (set (keys node))))
         (let [node-value (get node "$value")
               node-type (get node "$type")]
           (assoc-in acc (update path 0 design-tokens<>tailwind-configs) (hide-value-under-var-call path)))
         acc))
     {}
     (read-design-tokens! file))))

(defn figma-exported-design-tokens->tailwind-config
  [design-tokens-list]
  (mapv
   (fn [design-tokens-meta]
     (cond-> design-tokens-meta
       (= (:theme design-tokens-meta) "light")
       (assoc :tailwind-config (build-tailwind-config-from-design-tokens design-tokens-meta))))
   design-tokens-list))

(defn format-css-vars-groupped-by-theme
  [css-vars]
  (str/join #"; " (mapcat :css-vars css-vars)))

(defn inject-css-vars-to-template!
  []
  (let [css-template-file-content (slurp css-template-file)
        tailwind-template-file-content (slurp tailwind-config-template-file)
        design-tokens-with-css-vars (figma-exported-design-tokens->css-vars all-design-tokens)
        design-tokens-with-tailwind-configs (figma-exported-design-tokens->tailwind-config design-tokens-with-css-vars)
        {:strs [light dark]} (group-by :theme design-tokens-with-tailwind-configs)
        resulting-tailwind-config-json (json/generate-string
                                        (apply deep-merge (map :tailwind-config light))
                                        {:pretty true})]

    (spit resulting-css-file
          (-> css-template-file-content
              (str/replace "{{lightThemeVars}}" (format-css-vars-groupped-by-theme light))
              (str/replace "{{darkThemeVars}}" (format-css-vars-groupped-by-theme dark))))

    (spit resulting-tailwind-config-file
          (-> tailwind-template-file-content
              (str/replace "{{tailwindConfig}}" resulting-tailwind-config-json)))))

(inject-css-vars-to-template!)

(comment
  (inject-css-vars-to-template!)




  )
