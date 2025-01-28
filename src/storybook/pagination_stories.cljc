(ns storybook.pagination-stories
  (:require
   [hs-ui.core]
   [storybook.utils :as utils]))

(def ^:private page (utils/ratom 1))
(def ^:private rpp (utils/ratom 30))
(def ^:private total-pages 34)

(def ^:export default
  (clj->js
   {:title     "Molecules/Navigation"
    :component (utils/reagent-reactify-component hs-ui.core/pagination)
    :render    (fn [args]
                 (utils/reagent-as-element
                  [hs-ui.core/pagination (js->clj args {:keywordize-keys true})]))
    :args      {"page"            @page
                "total-pages"     total-pages
                "results-per-page" @rpp
                "c/on-page-change"  #(reset! page %)
                "c/on-rpp-change"   (fn [value] (reset! rpp (js/parseInt value)))}
    :argTypes  {"page"             {:control "number"}
                "total-pages"      {:control "number"}
                "results-per-page" {:control "number"}
                "c/on-page-change"   {:control "object"}
                "c/on-rpp-change"    {:control "object"}}}))

(def ^:export Pagination
  (clj->js {}))
