(ns storybook.org-tabs-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(defn example
  []
  (let [selected (utils/ratom "1")]
    (fn []
      [hs-ui.core/org-tabs
       {:name    "my-tabs"
        :c/items [{:id           "tab-1"
                   :slot/content "JS"
                   :c/selected?  (= "1" @selected)
                   :on-change    #(do (prn "1") (reset! selected "1"))}
                  {:id           "tab-2"
                   :slot/content "Python"
                   :c/selected?  (= "2" @selected)
                   :on-change    #(reset! selected "2")}
                  {:id           "tab-3"
                   :slot/content ".NET"
                   :c/selected?  (= "3" @selected)
                   :on-change    #(reset! selected "3")}]}])))

(def ^:export default
  (clj->js
   {:title     "Organisms/Navigation"
    :component (utils/reagent-reactify-component hs-ui.core/tab)
    :args      {"name"    "my-tabs"
                "c/items" [{"id" "1" "slot/content" "JS" "c/selected?" true :on-change (fn [])}
                           {"id" "2" "slot/content" "Python"}
                           {"id" "3" "slot/content" ".NET"}]}
    :argTypes  {"name"    {:control "text"}
                "c/items" {:control "object"}}}))

(def ^:export Tabs
  (clj->js
   {:render  (fn [args]
               (utils/reagent-as-element [example])) }))
