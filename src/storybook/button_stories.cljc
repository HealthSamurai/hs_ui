(ns storybook.button-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Buttons"
    :component (utils/reagent-reactify-component hs-ui.core/button)
    :render    (fn [args]
                 (let [args  (js->clj args {:keywordize-keys true})
                       props (dissoc args :_content)]
                   (utils/reagent-as-element [hs-ui.core/button props (:_content args)])))
    :args      {:_content   "Save"
                :isDisabled false
                :isLoading  false}
    :argTypes  {:variant    {:control "select" :options ["primary" "critical"]}
                :isDisabled {:control "boolean"}
                :isLoading  {:control "boolean"}
                :_content   {:control "text"}}}))

(def ^:export Primary
  (clj->js {:args {:variant "primary"}}))

(def ^:export Critical
  (clj->js {:args {:variant "critical"}}))
