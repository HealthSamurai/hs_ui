(ns storybook.radio-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Switches"
    :component (utils/reagent-reactify-component hs-ui.core/radio)
    :args      {:id       "my-id"
                :checked  false
                :disabled false}
    :argTypes  {:id       {:control "text"}
                :checked  {:control "boolean"}
                :disabled {:control "boolean"}}}))

(def ^:export Radio {})
