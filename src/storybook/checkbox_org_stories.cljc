(ns storybook.checkbox-org-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Organisms/Checkbox"
    :component (utils/reagent-reactify-component hs-ui.core/org-checkbox)
    :args      {:label     "New UI"
                :assistive "Better navigation and many other interface improvements"
                :checked   false
                :disabled  false}
    :argTypes  {:checked   {:control "boolean"}
                :disabled  {:control "boolean"}
                :label     {:control "text"}
                :assistive {:control "text"}}}))

(def ^:export Switch
  (clj->js {}))
