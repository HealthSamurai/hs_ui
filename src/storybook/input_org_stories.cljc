(ns storybook.input-org-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Organisms/Input"
    :component (utils/reagent-reactify-component hs-ui.core/org-input)
    :args      {"slot/label"     "Pool connection timeout"
                "slot/assistive" "Maximum time the connection pool will wait for a database connection before timing out "
                :data-invalid    false
                :placeholder     "3000"
                :disabled        false}
    :argTypes  {:placeholder     {:control "text"}
                :data-invalid    {:control "boolean"}
                :disabled        {:control "boolean"}
                "slot/label"     {:control "text"}
                "slot/assistive" {:control "text"}}}))

(def ^:export SingleLine
  (clj->js {}))
