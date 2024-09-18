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
  (clj->js {:argTypes {:disabled     {:control "boolean"}
                       :loading      {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:disabled false
                       :loading  false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-primary (js->clj args {:keywordize-keys true}) "Save"]))}))


(def ^:export Critical
  (clj->js {:argTypes {:disabled     {:control "boolean"}
                       :loading      {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:disabled false
                       :loading  false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-critical (js->clj args {:keywordize-keys true}) "Save"]))}))

(def ^:export Secondary
  (clj->js {:argTypes {:disabled     {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:disabled false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-secondary (js->clj args {:keywordize-keys true}) "Save"]))}))

(def ^:export Tertiary
  (clj->js {:argTypes {:disabled     {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:disabled false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-tertiary (js->clj args {:keywordize-keys true}) "Save"]))}))

(def ^:export XS
  (clj->js {:argTypes {:loading     {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:loading false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-xs (js->clj args {:keywordize-keys true}) "COPY"]))}))

(def ^:export XS_Red
  (clj->js {:argTypes {:loading      {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:loading false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-xs-red (js->clj args {:keywordize-keys true}) "VALIDATE"]))}))

(def ^:export Slim
  (clj->js {:argTypes {:disabled     {:control "boolean"}
                       :data-hovered {:control "boolean"}
                       :href         {:control "text"}}
            :args     {:disabled false
                       :data-hovered false}
            :render   (fn [args]
                        (utils/reagent-as-element [hs-ui.core/button-slim
                                                   (js->clj args {:keywordize-keys true})
                                                   "Label"
                                                   hs-ui.svg.template/svg]))}))
