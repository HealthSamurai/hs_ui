(ns storybook.org-text-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Organisms/Text"
    :component (utils/reagent-reactify-component hs-ui.core/org-header)}))

(def ^:export Header
  (clj->js
   {:args      {"slot/label" "Build"
                "slot/desc"  ["Lorem ipsum dolor sit amet, eu mei summo quodsi persecuti. "
                              [#'hs-ui.core/text-link {} "Learn more"]]}
    :argTypes  {"slot/label" {:control "text"}
                "slot/desc"  {:control "object"}}
    :render (fn [args]
              (utils/reagent-as-element
               [:div {:class "w-[331px]"}
                [hs-ui.core/org-header (js->clj args {:keywordize-keys true})]]))}))
