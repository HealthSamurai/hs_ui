(ns storybook.alert-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))


(def ^:export default
  (clj->js
   {:title     "Messages/Alert"
    :component (utils/reagent-reactify-component hs-ui.core/alert)
    :args      {:class "w-[612px]"}
    :argTypes  {:class    {:control "text"}
                :severity {:control "select" :options ["error"]}}}))

(def ^:export Error
  (clj->js {:args {:severity "error"}
            :render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/alert (js->clj args {:keywordize-keys true})
                        [hs-ui.core/text-body {:class "text-critical-default"} "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "]
                        [hs-ui.core/text-link {:class "text-critical-default"} "Learn more"]]))}))

(def ^:export Warning
  (clj->js {:args {:severity "warning"}
            :render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/alert (js->clj args {:keywordize-keys true})
                        [hs-ui.core/text-body {:class "text-elements-warning"} "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "]
                        [hs-ui.core/text-link {:class "text-elements-warning"} "Learn more"]]))}))

(def ^:export Info
  (clj->js {:args {:severity "info"}
            :render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/alert (js->clj args {:keywordize-keys true})
                        [hs-ui.core/text-body {:class "text-cta"} "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Learn more "]
                        [hs-ui.core/text-link {:class "text-cta"} "Learn more"]]))}))
