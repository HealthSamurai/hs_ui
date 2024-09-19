(ns hs-ui.components.button
 (:require [hs-ui.utils :as utils]
            [hs-ui.svg.loading]
            #?(:cljs ["@ariakit/react" :as kit])))

(def base-class
  ["px-[theme(spacing.x3)]"
   "py-[theme(spacing.x1)]"
   "h-[36px]"
   "inline-flex"
   "font-semibold"
   "rounded-[theme(borderRadius.corner-m)]"
   "select-none"
   "items-center"
   "disabled:cursor-not-allowed"
   "aria-busy:relative"
   "aria-busy:cursor-wait"])

(def primary-class
  ["shadow-button"
   "bg-[theme(colors.cta)]"
   "txt-button-label-regular"
   "!text-[theme(colors.elements-readable-inv)]"
   ;; Disabled
   "disabled:bg-[theme(colors.elements-disabled)]"
   ;; Loading
   "aria-busy:bg-[theme(colors.cta-hover)]"
   ;; Hovered
   "hover:bg-[theme(colors.cta-hover)]"
   "data-[hovered=true]:bg-[theme(colors.cta-hover)]"
   ;; Click
   "active:bg-[theme(colors.cta)]"])

(def critical-class
  ["shadow-button"
   "txt-button-label-regular"
   "bg-[theme(colors.critical-default)]"
   "!text-[theme(colors.elements-readable-inv)]"
   ;; Disabled
   "disabled:bg-[theme(colors.elements-disabled)]"
   ;; Loading
   "aria-busy:bg-[theme(colors.critical-hover)]"
   ;; Hovered
   "hover:bg-[theme(colors.critical-hover)]"
   "data-[hovered=true]:bg-[theme(colors.critical-hover)]"
   ;; Click
   "active:bg-[theme(colors.critical-default)]"])

(def secondary-class
  ["border"
   "txt-button-label-regular"
   "border-[theme(colors.border-default)]"
   "!text-[theme(colors.elements-assistive)]"
   ;; Disabled
   "disabled:!text-[theme(colors.elements-disabled)]"
   "disabled:bg-transparent"
   ;; Hovered
   "hover:!text-[theme(colors.elements-readable)]"
   "hover:bg-[theme(colors.surface-1)]"
   "data-[hovered=true]:!text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:bg-[theme(colors.surface-1)]"
   ;; Click
   "active:bg-transparent"])

(def tertiary-class
  ["!text-[theme(colors.elements-assistive)]"
   "txt-button-label-regular"
   ;; Disabled
   "disabled:!text-[theme(colors.elements-disabled)]"
   ;; Hovered
   "hover:!text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:!text-[theme(colors.elements-readable)]"
   ;; Click
   "active:!text-[theme(colors.elements-assistive)]"])

(def slim-class
  ["txt-label"
   "!text-[theme(colors.elements-assistive)]"
   "[&_svg]:ml-x1"
   "[&_svg]:enabled:text-[theme('colors.elements-readable')]"
   ;; Disabled
   "disabled:!text-[theme(colors.elements-disabled)]"
   ;; Hovered
   "hover:!text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:!text-[theme(colors.elements-readable)]"])

(def xs-class
  ["h-[24px]"
   "border"
   "border-[theme(colors.border-XS-regular)]"
   "rounded-[theme(borderRadius.corner-s)]"
   "bg-[theme(colors.surface-1)]"
   "txt-button-label-xs"
   "px-[theme(padding.x1)]"
   "pt-[2.25px]"
   "pb-[1.75px]"
   ;; Hovered
   "hover:bg-[theme(colors.surface-0)]"
   "hover:border-[theme(colors.border-XS-regular-hover)]"
   "data-[hovered=true]:bg-[theme(colors.surface-0)]"
   "data-[hovered=true]:border-[theme(colors.border-XS-regular-hover)]"
   ;; Loading
   "aria-busy:bg-[theme(colors.button-xs)]"
   "aria-busy:text-[theme(colors.border-XS-regular)]"])

(def xs-red-class
  ["h-[24px]"
   "border"
   "border-[theme(colors.border-XS-critical)]"
   "rounded-[theme(borderRadius.corner-s)]"
   "bg-[theme(colors.critical-default)]"
   "txt-button-label-xs"
   "!text-[theme(colors.elements-readable-inv)]"
   "px-[theme(padding.x1)]"
   "pt-[2.25px]"
   "pb-[1.75px]"
   ;; Hovered
   "hover:bg-[theme(colors.critical-hover)]"
   "hover:border-[theme(colors.border-XS-critical-hover)]"
   "data-[hovered=true]:bg-[theme(colors.critical-hover)]"
   "data-[hovered=true]:border-[theme(colors.border-XS-critical-hover)]"
   ;; Loading
   "aria-busy:bg-[theme(colors.critical-default)]"
   "aria-busy:!text-[theme(colors.border-XS-critical)]"
   "aria-busy:border-[theme(colors.border-XS-critical)]"])

(defn component
  [user-properties & children]
  (let [properties (dissoc user-properties :loading :variant)
        variant    (:variant user-properties "primary")
        loading    (:loading user-properties false)
        properties (cond-> properties loading (assoc :disabled true :aria-busy true))
        classes    (cond-> base-class
                     (= variant "primary")   (utils/class-names primary-class)
                     (= variant "critical")  (utils/class-names critical-class)
                     (= variant "secondary") (utils/class-names secondary-class)
                     (= variant "tertiary")  (utils/class-names tertiary-class)
                     (= variant "xs")        (utils/class-names xs-class)
                     (= variant "xs-red")    (utils/class-names xs-red-class)
                     (= variant "slim")      (utils/class-names slim-class))]
    (if (:href properties)
      [:a (utils/merge-props {:class [classes "text-link"]} properties) children]
      (into
       [:button (utils/merge-props {:class classes} properties)]
       (if (and loading (contains? #{"primary" "critical"} variant))
         [[:div.flex.items-center.justify-center.absolute.inset-0.animate-spin hs-ui.svg.loading/svg]
          [:span.contents.invisible children]]
         children)))))
