(ns storybook.checkbox-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Checkbox"
    :component (utils/reagent-reactify-component hs-ui.core/checkbox)
    :args      {:checked false
                :disabled false}
    :argTypes  {:checked  {:control "boolean"}
                :disabled {:control "boolean"}}}))

(def ^:export Switch
  (clj->js {}))
