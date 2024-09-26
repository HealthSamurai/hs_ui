(ns storybook.component-expand-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Switches"
    :component (utils/reagent-reactify-component hs-ui.core/content-expand)
    :render    (fn [args]
                 (utils/reagent-as-element
                  [hs-ui.core/content-expand (js->clj args {:keywordize-keys true})]))
    :args      {"c/open?"       false
                "slot/open"  "More"
                "slot/close" "Less"}
    :argTypes  {"c/open?"    {:control "boolean"}
                "slot/open"  {:control "text"}
                "slot/close" {:control "text"}}}))

(def ^:export ContentExpand (clj->js {}))
