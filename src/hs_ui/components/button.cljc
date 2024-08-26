(ns hs-ui.components.button
  (:require [hs-ui.utils :as utils]
            [hs-ui.svg.loading]
            #?(:cljs ["react-aria-components" :as c])))

(def base-class
  ["px-x3"
   "py-x1"
   "h-[36px]"
   "inline-flex"
   "rounded-m"
   "font-semibold"
   "select-none"
   "shadow-button-primary"])

(def primary-base-class
  ["bg-button-primary-default"
   "text-button-primary-text"
   "hover:bg-button-primary-hovered"
   "data-[disabled]:bg-button-primary-disabled"
   "data-[disabled]:cursor-not-allowed"
   "data-[aria-busy]:relative"
   "data-[aria-busy]:bg-button-primary-hovered"])

(def critical-base-class
  ["bg-button-critical-default"
   "text-button-critical-text"
   "hover:bg-button-critical-hovered"
   "data-[disabled]:bg-button-critical-disabled"
   "data-[disabled]:cursor-not-allowed"
   "data-[aria-busy]:relative"
   "data-[aria-busy]:bg-button-critical-hovered"])

(defn component
  [properties & children]
  (let [variant    (:variant properties "primary")
        loading    (:isLoading properties false)
        properties (cond-> properties
                     loading
                     (assoc :isDisabled true :data-aria-busy true))]
    [:> c/Button
     (utils/merge-props
      {:className (cond-> base-class
                    (= variant "primary")
                    (utils/class-names primary-base-class)
                    (= variant "critical")
                    (utils/class-names critical-base-class))}
      properties)
     (if loading [:span.contents.invisible children] children)
     (when loading [:div.flex.items-center.justify-center.absolute.inset-0 hs-ui.svg.loading/svg])]))
