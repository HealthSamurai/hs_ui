(ns storybook.button-stories
  (:require
   [hs-ui.core]
   [hs-ui.svg.template]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/Buttons"
    :component (utils/reagent-reactify-component hs-ui.core/button)}))

(def ^:export Primary
  (clj->js {:argTypes {:data-loading {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :data-active  {:control "boolean"}
                       :disabled     {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-loading false
                       :data-hovered false
                       :data-active  false
                       :disabled     false}
            :render (fn [args]
                      (utils/reagent-as-element [hs-ui.core/button-primary (js->clj args {:keywordize-keys true}) "Save"]))}))


(def ^:export Critical
  (clj->js {:argTypes {:data-loading {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :data-active  {:control "boolean"}
                       :disabled     {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-loading false
                       :data-hovered false
                       :data-active  false
                       :disabled     false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-critical (js->clj args {:keywordize-keys true}) "Save"]))}))

(def ^:export Secondary
  (clj->js {:argTypes {:data-hovered {:control "boolean"}
                       :data-active  {:control "boolean"}
                       :disabled     {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-hovered false
                       :data-active  false
                       :disabled     false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-secondary (js->clj args {:keywordize-keys true}) "Discard"]))}))

(def ^:export Tertiary
  (clj->js {:argTypes {:data-hovered {:control "boolean"}
                       :data-active  {:control "boolean"}
                       :disabled     {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-hovered false
                       :data-active  false
                       :disabled     false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-tertiary (js->clj args {:keywordize-keys true}) "Save"]))}))

(def ^:export XS
  (clj->js {:argTypes {:data-hovered {:control "boolean"}
                       :data-active  {:control "boolean"}
                       :loading      {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-hovered false
                       :data-active  false
                       :loading      false
                       :disabled     false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-xs (js->clj args {:keywordize-keys true}) "COPY"]))}))

(def ^:export XS_Red
  (clj->js {:argTypes {:data-hovered {:control "boolean"}
                       :loading      {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-hovered false
                       :loading      false}
            :render   (fn [args]
                        (utils/reagent-as-element [:div {:class "rounded-corner-s bg-critical-default w-screen p-5"}
                                                   [hs-ui.core/button-xs-red (js->clj args {:keywordize-keys true}) "VALIDATE"]]))}))

(def ^:export Slim
  (clj->js {:argTypes {:disabled     {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:data-hovered false
                       :disabled     false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-slim
                                                   (js->clj args {:keywordize-keys true})
                                                   "Label"
                                                   hs-ui.svg.template/svg]))}))
