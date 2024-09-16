(ns storybook.component-expand-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Switches"
    :component (utils/reagent-reactify-component hs-ui.core/content-expand)
    :args      {:open?      false
                :open-text  "More"
                :close-text "Less"}
    :argTypes  {:open?      {:control "boolean"}
                :open-text  {:control "text"}
                :close-text {:control "text"}}}))

(def ^:export ContentExpand (clj->js {}))
