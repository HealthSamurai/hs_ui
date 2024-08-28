(ns hs-ui.components.button
  (:require [hs-ui.utils :as utils]
            [hs-ui.svg.loading]
            #?(:cljs ["@ariakit/react" :as kit])))

(def base-class
  ["px-x3"
   "py-x1"
   "h-[36px]"
   "inline-flex"
   "rounded-m"
   "font-semibold"
   "select-none"
   "aria-busy:relative"
   "items-center"
   "focus:outline-none"
   "focus-visible:ring"])

(def primary-class
  ["shadow-button-primary"
   "bg-button-primary-default"
   "text-button-primary-text"
   "hover:bg-button-primary-hovered"
   "disabled:bg-button-primary-disabled"
   "aria-busy:bg-button-primary-disabled"])

(def critical-class
  ["shadow-button-critical"
   "bg-button-critical-default"
   "text-button-critical-text"
   "hover:bg-button-critical-hovered"
   "disabled:bg-button-critical-disabled"
   "aria-busy:bg-button-critical-disabled"])

(def secondary-class
  ["border"
   "border-border-default"
   "bg-button-secondary-default"
   "text-button-secondary-text-default"
   "disabled:text-button-secondary-text-disabled"
   "hover:text-button-secondary-text-hovered"
   "hover:bg-button-secondary-hovered"])

(def tertiary-class
  ["text-button-tertiary-text-default"
   "hover:text-button-tertiary-text-hovered"
   "disabled:text-button-tertiary-text-disabled"])

(defn component
  [user-properties & children]
  (let [properties (dissoc user-properties :loading :variant)
        variant    (:variant user-properties "primary")
        loading    (:loading user-properties false)
        properties (cond-> properties
                     loading
                     (assoc :disabled true
                            :aria-busy true))]
    [:>
     #?(:cljs kit/Button)
     (utils/merge-props
      {:className (cond-> base-class
                    (= variant "primary")   (utils/class-names primary-class)
                    (= variant "critical")  (utils/class-names critical-class)
                    (= variant "secondary") (utils/class-names secondary-class)
                    (= variant "tertiary")  (utils/class-names tertiary-class))}
      properties)
     (if loading [:span.contents.invisible children] children)
     (when loading [:div.flex.items-center.justify-center.absolute.inset-0 hs-ui.svg.loading/svg])]))
