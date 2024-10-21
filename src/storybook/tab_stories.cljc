(ns storybook.tab-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Navigation"
    :component (utils/reagent-reactify-component hs-ui.core/tab)
    :render    (fn [args]
                 (utils/reagent-as-element
                  [hs-ui.core/tab (js->clj args {:keywordize-keys true})]))
    :args      {"data-hovered"  false
                "c/selected?"   false
                "slot/content"  "Tab label"}
    :argTypes  {"data-hovered"  {:control "boolean"}
                "c/selected?"   {:control "boolean"}
                "slot/content"  {:control "text"}}}))

(def ^:export Tab
  (clj->js {}))
