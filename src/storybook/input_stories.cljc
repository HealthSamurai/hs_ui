(ns storybook.input-stories
  (:require
   [hs-ui.core]
   [hs-ui.svg.search]
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
                :class            {:control "text"}
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
                                            [hs-ui.text/value {:class "text-[theme(colors.elements-assistive)]"} "milliseconds"]
                                            [hs-ui.core/button-xs {} "COPY"]])]))}))

(def ^:export SlotLeft
  (clj->js {:args   {"c/root-class" "h-[32px] w-[407px]"
                     :placeholder "Search"}
            :render (fn [args]
                      (utils/reagent-as-element
                       [hs-ui.core/input
                        (assoc (js->clj args {:keywordize-keys true})
                               :slot/left hs-ui.svg.search/svg)]))}))
