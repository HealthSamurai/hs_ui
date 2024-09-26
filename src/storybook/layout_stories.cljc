(ns storybook.layout-stories
  (:require
   [hs-ui.core]
   [hs-ui.svg.download]
   [storybook.utils :as utils]))

(def ^:export default
  (clj->js
   {:title     "Layout"
    :component (utils/reagent-reactify-component hs-ui.core/layout-confirmation)}))

(def ^:export Confirmation
  (clj->js
   {:args      {"slot/right" [[#'hs-ui.core/button-tertiary {} "Cancel"]
                              [#'hs-ui.core/button-primary {} "Save"]]}
    :argTypes  {"slot/right" {:control "object"}}
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/layout-confirmation (js->clj args {:keywordize-keys true})]))}))

(def ^:export Nest
  (clj->js
   {:args      {"slot/control" [#'hs-ui.core/org-input {"slot/label" "Pool connection timeout"
                                                        "slot/assistive" "Maximum time the connection pool will wait for a database connection before timing out"}]
                "slot/confirmation" [[#'hs-ui.core/button-tertiary {:class "w-[112px]"} "Cancel"]
                                     [#'hs-ui.core/button-primary {:class "w-[128px]"} "Save"]]
                "c/show?"           true}
    :argTypes  {"slot/control"      {:control "object"}
                "slot/confirmation" {:control "object"}
                "c/show?"           {:control "boolean"}
                }
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/layout-nest (js->clj args {:keywordize-keys true})]))}))

(def ^:export Navbar
  (clj->js
   {:args      {"slot/left" [hs-ui.core/text-page-header {} "Settings"]
                "slot/middle" [hs-ui.core/org-search-input {"c/variant" "rounded"}]
                "slot/right" [hs-ui.core/button-slim {} "Export YAML" hs-ui.svg.download/svg]}
    :argTypes  {"slot/right"  {:control "object"}
                "slot/middle" {:control "object"}
                "slot/left"   {:control "object"}}
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/layout-navbar (js->clj args {:keywordize-keys true})]))}))
