(ns storybook.text-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title "Atoms/Text Styles"}))

(defn ^:export PageHeader
  [props]
  (utils/reagent-as-element [hs-ui.core/text-page-header {} "Page header"]))

(defn ^:export SectionHeader
  [props]
  (utils/reagent-as-element [hs-ui.core/text-section-header {} "Section header"]))

(defn ^:export Label
  [props]
  (utils/reagent-as-element [hs-ui.core/text-label {} "Label"]))

(defn ^:export Assistive
  [props]
  (utils/reagent-as-element [hs-ui.core/text-assistive {} "Assistive"]))
