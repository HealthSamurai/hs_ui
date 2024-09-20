(ns storybook.breadcrumbs-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Navigation"
    :component (utils/reagent-reactify-component hs-ui.core/breadcrumbs)
    :args      {:body "Back"
                :href "#/"
                :data-hovered false}
    :argTypes  {:body         {:control "text"}
                :href         {:control "text"}
                :data-hovered {:control "boolean"}}}))

(def ^:export Breadcrumbs {})
