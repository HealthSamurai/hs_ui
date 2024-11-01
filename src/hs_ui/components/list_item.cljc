(ns hs-ui.components.list-item
  (:require [hs-ui.utils :as utils]))

(def root-class
  ["flex"
   "items-center"
   "h-[32px]"
   "rounded-[theme(borderRadius.corner-s)]"
   "txt-value"
   "text-[theme(colors.elements-assistive)]"
   "pr-[theme(spacing.x1point5)]"
   "pl-[theme(spacing.x1point5)]"
   "[&_svg]:mr-[7px]"
   ;; Hover
   "hover:bg-[theme(colors.surface-1)]"
   "data-[hovered=true]:bg-[theme(colors.surface-1)]"
   "cursor-pointer"
   ;; Selected
   "data-[selected=true]:bg-[theme(colors.surface-selected)]"
   "data-[selected=true]:text-[theme(colors.elements-readable)]"
   ])

(defn component
  [props & children]
  (into
   [(if (:href props) :a :div) (utils/merge-props {:class root-class} props)]
   children))
