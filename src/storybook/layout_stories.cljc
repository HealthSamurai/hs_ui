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

(def ^:export ExpandeableControl
  (clj->js
   {:args      {"slot/control" [#'hs-ui.core/org-input {"slot/label"     "Pool connection timeout"
                                                        "slot/assistive" "Maximum time the connection pool will wait for a database connection before timing out"
                                                        "c/expand?"      true}]
                "c/expand?"    false
                "slot/content" [#'hs-ui.core/kvlist {"c/items"
                                                     [{:key 1 "slot/key" "Name:" "slot/value" [:span "web.max-body" [hs-ui.core/button-xs {:class "ml-x1"} "COPY"]]}
                                                      {:key 2 "slot/key" "Default:" "slot/value" "20971520"}
                                                      {:key 3 "slot/key" "ENV Alias:" "slot/value" "BOX_WEB_MAX_BODY"}
                                                      {:key 4 "slot/key" "Value introspect config:" "slot/value" "https://skynet.aidbox.app (set-by-user)"}]}]}
    :argTypes  {"slot/control" {:control "object"}
                "slot/content" {:control "object"}
                "c/expand?"    {:control "boolean"}}
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/layout-expandeable-control (js->clj args {:keywordize-keys true})]))}))

(def ^:export VerticalSplitView
  (clj->js
   {:args      {"c/default-left-percent" 40
                "c/min-left-percent" 10
                "c/visible-on-hover" true}
    :argTypes  {"c/default-left-percent" {:control "number"}
                "c/min-left-percent" {:control "number"}
                "c/default-right-percent" {:control "number"}
                "c/min-right-percent" {:control "number"}
                "c/visible-on-hover" {:control "boolean"}
                "c/separator-class" {:control "text"}}
    :render (fn [args]
              (utils/reagent-as-element
               [hs-ui.core/layout-vertical-split-view
                (js->clj args {:keywordize-keys true})
                [:div {:class "h-full w-full"} "HELLO"]
                [:div {:class "h-full w-full shadow-[0px_8px_8px_0px_rgba(29,35,49,0.20)]"} "THERE"]]))}))

;; FIXME: Broken in storybook. Might be that the storybook setup is broken.
(def ^:export HorizontalSplitView
  (clj->js
   {:args      {"c/default-upper-percent" 40
                "c/min-upper-percent" 10
                "c/visible-on-hover" true}
    :argTypes  {"c/default-upper-percent" {:control "number"}
                "c/min-upper-percent" {:control "number"}
                "c/default-lower-percent" {:control "number"}
                "c/min-lower-percent" {:control "number"}
                "c/visible-on-hover" {:control "boolean"}
                "c/separator-class" {:control "text"}}
    :render (fn [args]
              (utils/reagent-as-element
               [:div {:class "h-full"}
                [hs-ui.core/layout-horizontal-split-view
                 (js->clj args {:keywordize-keys true})
                 [:div {:class "h-full w-full shadow-[0px_8px_8px_0px_rgba(29,35,49,0.20)]"} "HELLO"]
                 [:div {:class "h-full w-full bg-red"}
                  "THERE"]]]))}))
