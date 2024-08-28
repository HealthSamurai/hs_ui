(ns hs-ui.components.button
  (:require [hs-ui.utils :as utils]
            [hs-ui.svg.loading]
            #?(:cljs ["@ariakit/react" :as kit])))

(def base-class
  ["px-[theme(padding.x3)]"
   "py-[theme(padding.x1)]"
   "h-[36px]"
   "inline-flex"
   "border"
   "border-[theme(colors.border-default)]"
   "font-semibold"
   "rounded-[theme(borderRadius.m)]"
   "select-none"
   "aria-busy:relative"
   "items-center"
   "focus:outline-none"
   "focus-visible:ring"])

(def primary-class
  ["shadow-[theme(boxShadow.button-primary)]"
   "bg-[theme(backgroundColor.button-primary-default)]"
   "text-[theme(colors.button-primary-text)]"
   "hover:bg-[theme(backgroundColor.button-primary-hovered)]"
   "disabled:bg-[theme(backgroundColor.button-primary-disabled)]"
   "aria-busy:bg-[theme(backgroundColor.button-primary-disabled)]"])

(def critical-class
  ["shadow-[theme(boxShadow.button-critical)]"
   "bg-[theme(backgroundColor.button-critical-default)]"
   "text-[theme(colors.button-critical-text)]"
   "hover:bg-[theme(backgroundColor.button-critical-hovered)]"
   "disabled:bg-[theme(backgroundColor.button-critical-disabled)]"
   "aria-busy:bg-[theme(backgroundColor.button-critical-disabled)]"])

(def secondary-class
  ["bg-[theme(backgroundColor.button-secondary-default)]"
   "text-[theme(colors.button-secondary-text)]"
   "disabled:text-[theme(colors.button-secondary-text-default)]"
   "hover:text-[theme(colors.button-secondary-text-hovered)]"
   "hover:bg-[theme(backgroundColor.button-secondary-hovered)]"])

(def tertiary-class
  ["border-transparent"
   "text-[theme(colors.button-tertiary-text-default)]"
   "font-semibold"
   "hover:text-[theme(colors.button-tertiary-text-hovered)]"
   "disabled:text-[theme(colors.button-tertiary-text-disabled)]"])

(def xs-class
  ["h-auto"
   "bg-[theme(backgroundColor.button-xs)]"
   "text-[theme(colors.button-xs-text)]"
   "border-[theme(borderColor.button-xs)]"
   "rounded-[theme(borderRadius.s)]"
   "font-size-[theme(fontSize.button-xs)]"
   "leading-[theme(lineHeight.button-xs)]"
   "px-[theme(padding.x1)]"
   "font-medium"
   "py-[1.5px]"
   "hover:bg-[theme(backgroundColor.button-xs-hovered)]"
   "hover:border-[theme(borderColor.button-xs-hovered)]"
   "disabled:bg-[theme(backgroundColor.button-xs)]"
   "disabled:text-[theme(colors.button-xs-text-disabled)]"
   "aria-busy:bg-[theme(backgroundColor.button-xs)]"
   "aria-busy:text-[theme(colors.button-xs-text-disabled)]"])

(defn component
  [user-properties & children]
  (let [properties (dissoc user-properties :loading :variant)
        variant    (:variant user-properties "primary")
        loading    (:loading user-properties false)
        properties (cond-> properties
                     loading
                     (assoc :disabled true
                            :aria-busy true))
        classes (cond-> base-class
                  (= variant "primary")   (utils/class-names primary-class)
                  (= variant "critical")  (utils/class-names critical-class)
                  (= variant "secondary") (utils/class-names secondary-class)
                  (= variant "tertiary")  (utils/class-names tertiary-class)
                  (= variant "xs")        (utils/class-names xs-class)
                  (not (:loading user-properties)) (utils/class-names ["[&_svg]:mr-[theme(margin.x1)]" "[&_svg]:text-icon"]))]
    (if (:href properties)
      [:a (utils/merge-props {:class [classes "text-link"]} properties) children]
      [:>
       #?(:cljs kit/Button)
       (utils/merge-props {:className classes} properties)
       (if loading [:span.contents.invisible children] children)
       (when loading [:div.flex.items-center.justify-center.absolute.inset-0 hs-ui.svg.loading/svg])])))
