(ns hs-ui.components.input
  (:require [hs-ui.utils :as utils]
            #?(:cljs ["@ariakit/react" :as kit])))

(def base-class
  ["flex"
   "w-[407px]"
   "h-[36px]"
   "px-3"
   "py-1.5"
   "justify-between"
   "items-center"
   "flex-shrink-0"
   "border"
   "border-[theme(input.default)]"
   "rounded-[theme(borderRadius.m)]"
   "bg-[theme(backgroundColor.input-background)]"])

(defn component
  [user-properties & children]
  [:>
   #?(:cljs kit/FormInput)
   (utils/merge-props {:className (utils/class-names base-class)} user-properties)]
  )
