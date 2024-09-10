(ns storybook.input-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]
   #?(:cljs ["@ariakit/react" :as kit])))

(def base-class
  ["border"
   "border-[theme(input.default)]"])

(def ^:export default
  (clj->js
   {:title  "Molecules/Input"
    :render (fn [args]
              (let [args  (js->clj args {:keywordize-keys true})
                    props (dissoc args :_content)]
                (utils/reagent-as-element
                 [:>
                  kit/FormProvider
                  [:>
                   kit/Form
                   [hs-ui.core/input props (:_content args)]]])))
    :component (utils/reagent-reactify-component hs-ui.core/input)
    :args      {:placeholder "example"
                :disabled    false
                :class       "w-[407px]"}
    :argTypes  {:placeholder {:control "text"}
                :disabled    {:control "boolean"}
                :class       {:control "text"}}}))

(def ^:export Single-line (clj->js {}))
