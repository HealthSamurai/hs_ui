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

(defn ^:export ButtonLabelRegular
  [props]
  (utils/reagent-as-element [hs-ui.core/text-button-label-regular {} "Button label regular"]))

(defn ^:export Label
  [props]
  (utils/reagent-as-element [hs-ui.core/text-label {} "Label"]))

(defn ^:export Link
  [props]
  (utils/reagent-as-element [hs-ui.core/text-link {:href "#/"} "Link"]))

(defn ^:export Value
  [props]
  (utils/reagent-as-element [hs-ui.core/text-value {} "Value"]))

(defn ^:export Body
  [props]
  (utils/reagent-as-element [hs-ui.core/text-body {} "Join our meet up to hear from experts in SQL on FHIR as they share insights on simplifying FHIR data processing and creating flat views for efficient analysis. Learn best practices for integrating with electronic medical record systems and overcoming implementation challenges.

This event offers a unique opportunity to deepen your understanding and network with industry leaders in healthcare technology. Engage with professionals, ask questions, and explore real-world applications of this innovative technology."]))

(defn ^:export Code
  [props]
  (utils/reagent-as-element [hs-ui.core/text-code {} "Code"]))

(defn ^:export Counter
  [props]
  (utils/reagent-as-element [hs-ui.core/text-counter {} "Counter"]))

(defn ^:export ButtonLabelXs
  [props]
  (utils/reagent-as-element [hs-ui.core/text-button-label-xs {} "Button label XS"]))

(defn ^:export Assistive
  [props]
  (utils/reagent-as-element [hs-ui.core/text-assistive {} "Unknown polymorphic extensions will be automatically transformed to Aidbox format. Ex.: extension.*.valueString -> extension.0.value.string"]))
