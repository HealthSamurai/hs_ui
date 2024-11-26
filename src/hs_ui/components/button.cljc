(ns hs-ui.components.button
 (:require [hs-ui.utils :as utils]
           [hs-ui.svg.loading]))

(def base-class
  ["px-[theme(spacing.x3)]"
   "py-[theme(spacing.x1)]"
   "h-[36px]"
   "inline-flex"
   "justify-center"
   "items-center"
   "focus:outline-none"
   "rounded-[theme(borderRadius.corner-m)]"
   "select-none"
   "disabled:cursor-not-allowed"
   "data-[loading=true]:relative"
   "data-[loading=true]:cursor-wait"
   "whitespace-nowrap"
   "truncate"])

(def primary-class
  ["shadow-button"
   "bg-[theme(colors.cta)]"
   "txt-button-label-regular"
   "text-[theme(colors.elements-readable-inv)]"
   ;; Disabled
   "disabled:bg-[theme(colors.elements-disabled)]"
   ;; Loading
   "data-[loading=true]:bg-[theme(colors.cta-hover)]"
   ;; Hovered
   "hover:bg-[theme(colors.cta-hover)]"
   "data-[hovered=true]:bg-[theme(colors.cta-hover)]"
   ;; Click
   "active:bg-[theme(colors.cta)]"
   "data-[active=true]:bg-[theme(colors.cta)]"
   ])

(def critical-class
  ["shadow-button"
   "txt-button-label-regular"
   "bg-[theme(colors.critical-default)]"
   "text-[theme(colors.elements-readable-inv)]"
   ;; Disabled
   "disabled:bg-[theme(colors.elements-disabled)]"
   ;; Loading
   "data-[loading=true]:bg-[theme(colors.critical-hover)]"
   ;; Hovered
   "hover:bg-[theme(colors.critical-hover)]"
   "data-[hovered=true]:bg-[theme(colors.critical-hover)]"
   ;; Click
   "active:bg-[theme(colors.critical-default)]"
   "data-[active=true]:bg-[theme(colors.critical-default)]"])

(def secondary-class
  ["border"
   "txt-button-label-regular"
   "border-[theme(colors.border-default)]"
   "text-[theme(colors.elements-assistive)]"
   ;; Disabled
   "disabled:text-[theme(colors.elements-disabled)]"
   "disabled:bg-transparent"
   ;; Hovered
   "hover:text-[theme(colors.elements-readable)]"
   "hover:bg-[theme(colors.surface-1)]"
   "data-[hovered=true]:text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:bg-[theme(colors.surface-1)]"
   ;; Click
   "active:bg-transparent"
   "data-[active=true]:bg-transparent"])

(def tertiary-class
  ["text-[theme(colors.elements-assistive)]"
   "txt-button-label-regular"
   ;; Disabled
   "disabled:text-[theme(colors.elements-disabled)]"
   ;; Hovered
   "hover:text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:text-[theme(colors.elements-readable)]"
   ;; Click
   "active:text-[theme(colors.elements-assistive)]"
   "data-[active=true]:text-[theme(colors.elements-assistive)]"])

(def slim-class
  ["txt-label"
   "p-0"
   "h-auto"
   "text-[theme(colors.elements-assistive)]"
   "[&_svg]:ml-x1"
   "[&_svg]:enabled:text-[theme(colors.elements-readable)]"
   ;; Disabled
   "disabled:text-[theme(colors.elements-disabled)]"
   ;; Hovered
   "hover:text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:text-[theme(colors.elements-readable)]"])

(def xs-class
  ["h-[24px]"
   "border"
   "border-[theme(colors.border-XS-regular)]"
   "rounded-[theme(borderRadius.corner-s)]"
   "bg-[theme(colors.surface-1)]"
   "txt-button-label-xs"
   "text-[theme(colors.cta)]"
   "px-0"
   "py-0"
   "pr-[3.5px]"
   "pl-[3.2px]"
   "pt-[2.25px]"
   "pb-[1.75px]"
   ;; Hovered
   "hover:bg-[theme(colors.surface-0)]"
   "hover:border-[theme(colors.border-XS-regular-hover)]"
   "data-[hovered=true]:bg-[theme(colors.surface-0)]"
   "data-[hovered=true]:border-[theme(colors.border-XS-regular-hover)]"
   ;; Loading
   "data-[loading=true]:bg-[theme(colors.surface-1)]"
   "data-[loading=true]:text-[theme(colors.border-XS-regular)]"
   "group"])

(def xs-red-class
  ["h-[24px]"
   "border"
   "border-[theme(colors.border-XS-critical)]"
   "rounded-[theme(borderRadius.corner-s)]"
   "bg-[theme(colors.critical-default)]"
   "txt-button-label-xs"
   "text-[theme(colors.elements-readable-inv)]"
   "px-0"
   "py-0"
   "pr-[7.5px]"
   "pl-[7.2px]"
   "pt-[2.25px]"
   "pb-[1.75px]"
   ;; Hovered
   "hover:bg-[theme(colors.critical-hover)]"
   "hover:border-[theme(colors.border-XS-critical-hover)]"
   "data-[hovered=true]:bg-[theme(colors.critical-hover)]"
   "data-[hovered=true]:border-[theme(colors.border-XS-critical-hover)]"
   ;; Loading
   "data-[loading=true]:bg-[theme(colors.critical-default)]"
   "data-[loading=true]:text-[theme(colors.border-XS-critical)]"
   "data-[loading=true]:border-[theme(colors.border-XS-critical)]"])

(defn get-element-name
  [properties]
  (cond
    (:href properties) :a
    (:c/label? properties) :label
    :else :button))

(defn loading-icon
  [properties children]
  [:<>
   [:div.flex.items-center.justify-center.absolute.animate-spin
    (if (:c/loading-icon properties) (:c/loading-icon properties) hs-ui.svg.loading/svg)]
   [:span.contents.invisible children]])

(defn component
  [properties children]
  (let [loading?   (:data-loading properties)
        properties (cond-> properties
                     loading? (assoc :disabled true))]
    [(get-element-name properties)
     (utils/merge-props {:class base-class} properties)
     (if loading? (loading-icon properties children) children)]))

(defn primary
  [& arguments]
  (component
   (utils/merge-properties
    {:class primary-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))

(defn critical
  [& arguments]
  (component
   (utils/merge-properties
    {:class critical-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))

(defn secondary
  [& arguments]
  (component
   (utils/merge-properties
    {:class secondary-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))

(defn tertiary
  [& arguments]
  (component
   (utils/merge-properties
    {:class tertiary-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))

(defn xs
  [& arguments]
  (component
   (utils/merge-properties
    {:class xs-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))

(defn xs-red
  [& arguments]
  (component
   (utils/merge-properties
    {:class xs-red-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))

(defn slim
  [& arguments]
  (component
   (utils/merge-properties
    {:class slim-class}
    (utils/get-component-properties arguments))
   (utils/get-component-children arguments)))
