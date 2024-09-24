(ns storybook.input-stories
  (:require
   [hs-ui.core]
   [hs-ui.svg.search]
   [hs-ui.svg.template]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Input"
    :component (utils/reagent-reactify-component hs-ui.core/input)
    :args      {:placeholder    "example"
                :data-invalid   false
                :disabled       false
                "c/root-class"  "w-[407px]"}
    :argTypes  {:placeholder      {:control "text"}
                :value            {:control "text"}
                :data-invalid     {:control "boolean"}
                :disabled         {:control "boolean"}}}))

(def ^:export Default
  (clj->js {:render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/input (js->clj args {:keywordize-keys true})]))}))

(def ^:export SlotRight
  (clj->js {:render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/input
                        (assoc (js->clj args {:keywordize-keys true})
                               :slot/right [:<>
                                            [hs-ui.text/value {:class "text-[theme(colors.elements-assistive)] pr-[2px]"} "milliseconds"]
                                            [hs-ui.core/button-xs {} "COPY"]])]))}))

(def ^:export SlotLeft
  (clj->js {:args   {"c/root-class" "w-[407px]"
                     :placeholder "example"}
            :render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/input
                        (assoc (js->clj args {:keywordize-keys true})
                               :slot/left [:span.text-elements-assistive hs-ui.svg.template/svg])]))}))

(def ^:export SearchInput
  (clj->js {:args   {"c/root-class" "w-[407px]"
                     "c/variant"    "squared"
                     :placeholder "Search"}
            :argTypes {"c/variant" {:control "select" :options ["rounded" "squared"]}}
            :render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/org-search-input (js->clj args {:keywordize-keys true})]))}))
