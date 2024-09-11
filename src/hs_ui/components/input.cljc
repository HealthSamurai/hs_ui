(ns hs-ui.components.input
  (:require [hs-ui.utils :as utils]
            #?(:cljs ["@ariakit/react" :as kit])))

(def base-class
  ["flex"
   "font-normal"
   "w-full"
   "h-[36px]"
   "px-3"
   "py-1.5"
   "justify-between"
   "items-center"
   "flex-shrink-0"
   "border"
   "border-[theme(colors.color-border-default)]"
   "rounded-[theme(borderRadius.m)]"
   "bg-[theme(backgroundColor.input-background)]"
   "text-[theme(textColor.elements-readable)]"
   "placeholder:text-[theme(textColor.color-elements-disabled)]"
   "shadow-[theme(boxShadow.input-default)]"
   "outline-[theme(colors.color-cta)]"
   "disabled:bg-[theme(colors.color-surface-1)]"
   "disabled:text-[theme(colors.color-elements-assistive)]"])

(defn component
  [user-properties & children]
  [:input
   (utils/merge-props {:className (utils/class-names base-class (:class user-properties))}
                      (dissoc user-properties :class))])
