(ns hs-ui.components.list-item
  (:require [hs-ui.utils :as utils]))

(def root-class
  ["flex"
   "items-center"
   "h-[32px]"
   "rounded-[theme(borderRadius.corner-s)]"
   "txt-value"
   "!text-[theme(colors.elements-assistive)]"
   "px-[theme(spacing.x1point5)]"
   "[&_svg]:mr-[theme(spacing.x1)]"
   ;; Hover
   "hover:bg-[theme(colors.surface-1)]"
   "data-[hovered=true]:bg-[theme(colors.surface-1)]"
   ;; Selected
   "data-[selected=true]:bg-[theme(colors.surface-selected)]"
   "data-[selected=true]:!text-[theme(colors.elements-readable)]"
   ])

(defn component
  [props & children]
  (into
   [:div (utils/merge-props {:class root-class} props)]
   children))
