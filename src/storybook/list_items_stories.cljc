(ns storybook.list-items-stories
  (:require
   [hs-ui.core]
   [hs-ui.svg.item]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/List"
    :component (utils/reagent-reactify-component hs-ui.core/list-items)
    :args      {:class "w-[304px]"}
    :argTypes  {:class {:control "text"}}
    }))


(def ^:export ListItems
  (clj->js
   {:render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/list-items (js->clj args {:keywordize-keys true})
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "Account"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "ActivityDefinition"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "AdverseEvent"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "AllergyIntolerance"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "ActivityAppointment"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "MedicinalProductUndesirableEffect"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "ObservationDefinition"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "Patient"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "QuestionnaireResponse"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "Request"]
                [hs-ui.core/list-item {} hs-ui.svg.item/svg "RequestGroup"]]))}))

(def ^:export KVList
  (clj->js
   {
    :args      {:class "w-[304px]"
                "c/items" [{:key 1 "slot/key" "Name:" "slot/value" [:span "web.max-body" [hs-ui.core/button-xs {:class "ml-x1"} "COPY"]]}
                           {:key 2 "slot/key" "Default:" "slot/value" "20971520"}
                           {:key 3 "slot/key" "ENV Alias:" "slot/value" "BOX_WEB_MAX_BODY"}
                           {:key 4 "slot/key" "Value introspect config:" "slot/value" "https://skynet.aidbox.app (set-by-user)"}]}
    :argTypes  {:class {:control "text"}
                "c/items" {:control "object"}}
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/kvlist (js->clj args {:keywordize-keys true})]))}))
