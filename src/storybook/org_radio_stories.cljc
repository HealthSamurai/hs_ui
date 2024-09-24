(ns storybook.org-radio-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Organisms/Switches"
    :component (utils/reagent-reactify-component hs-ui.core/org-radio)
    :render    (fn [args]
                 (utils/reagent-as-element [hs-ui.core/org-radio (js->clj args {:keywordize-keys true})]))
    :args      {"slot/label"     "Lorem ipsum dolor sit amet"
                "slot/assistive" "Better navigation and many other interface improvements"
                "c/options"      [{"slot/label" "Lorem ipsum dolor sit amet" :checked true}
                                  {"slot/label" "Lorem ipsum dolor sit amet"}
                                  {"slot/label" "Lorem ipsum dolor sit amet"}
                                  {"slot/label" "Lorem ipsum dolor sit amet"}]
                :disabled        false}
    :argTypes  {:disabled        {:control "boolean"}
                "slot/label"     {:control "text"}
                "slot/assistive" {:control "text"}
                "c/options"      {:control "object"}}}))

(def ^:export Radio
  (clj->js {}))
