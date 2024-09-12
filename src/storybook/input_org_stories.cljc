(ns storybook.input-org-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Organisms/Input"
    :component (utils/reagent-reactify-component hs-ui.core/org-input)
    :args      {:label       "Pool connection timeout"
                :assistive   "Maximum time the connection pool will wait for a database connection before timing out "
                :placeholder "3000"
                :disabled    false}
    :argTypes  {:placeholder {:control "text"}
                :disabled  {:control "boolean"}
                :label     {:control "text"}
                :assistive {:control "text"}}}))

(def ^:export SingleLine
  (clj->js {}))
