(ns storybook.radio-button-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Switches"
    :component (utils/reagent-reactify-component hs-ui.core/radio-button)
    :args      {:id       "my-id"
                :checked  false
                :disabled false
                "slot/label" "Latest"
                "slot/desc"  "Newest features, bug fixes, and optimizations, QA Passed."}
    :argTypes  {:id          {:control "text"}
                "slot/label" {:control "text"}
                "slot/desc"  {:control "text"}
                :checked     {:control "boolean"}
                :disabled    {:control "boolean"}}}))

(def ^:export RadioButton {})
