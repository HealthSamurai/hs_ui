(ns storybook.layout-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Layout"
    :component (utils/reagent-reactify-component hs-ui.core/layout-confirmation)}))

(def ^:export Confirmation
  (clj->js
   {:args      {"slot/right" [[#'hs-ui.core/button-tertiary {} "Cancel"]
                              [#'hs-ui.core/button-primary {} "Save"]]}
    :argTypes  {"slot/right" {:control "object"}}
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/layout-confirmation (js->clj args {:keywordize-keys true})]))}))
