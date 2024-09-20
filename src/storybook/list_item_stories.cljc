(ns storybook.list-item-stories
  (:require
   [hs-ui.core]
   [hs-ui.svg.item]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Molecules/List"
    :component (utils/reagent-reactify-component hs-ui.core/list-item)
    :args      {:class         "w-[304px]"
                :data-hovered  false
                :data-selected false}
    :argTypes  {:class         {:control "text"}
                :data-hovered  {:control "boolean"}
                :data-selected {:control "boolean"}}}))

(def ^:export ListItem
  (clj->js
   {:render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/list-item (js->clj args {:keywordize-keys true})
                hs-ui.svg.item/svg
                "Account"]))}))
