(ns storybook.dropdown-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Organisms/Dropdowns"
    :component (utils/reagent-reactify-component hs-ui.core/dropdown )
    :render    (fn [args]
                 (let [args  (js->clj args {:keywordize-keys true})
                       props (dissoc args :_content)]
                   (utils/reagent-as-element [:div {:style {:width 312}}
                                              [hs-ui.core/dropdown  props (:_content args)]])))
    :args      {:_content "Save"
                :disabled false
                :loading  false}
    :argTypes  {:variant  {:control "select" :options ["primary" "critical" "tertiary"]}
                :disabled {:control "boolean"}
                :loading  {:control "boolean"}
                :_content {:control "text"}}}))

(def ^:export Combobox (clj->js {}))
