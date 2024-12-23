(ns hs-ui.components.content-expand
  (:require [hs-ui.svg.extra-hide]
            [hs-ui.svg.extra-hide-filled]
            [hs-ui.svg.extra-show]
            [hs-ui.svg.extra-show-filled]
            [hs-ui.utils]))

(def root-class
  ["flex"
   "items-center"
   "justify-end"
   "cursor-pointer"
   "group"
   "w-full"
   "text-[theme(colors.elements-assistive)]"
   "txt-assistive"
   "select-none"])

(defn component
  "Props:
  :c/open?
  :slot/close
  :slot/open"
  [props]
  [:div (hs-ui.utils/merge-props {:class root-class} props)
   (if (:c/open? props)
     [:<>
      [:span {:class "visible text-[theme(colors.elements-readable)]"} (:slot/close props "Less")]
      [:span {:class "visible group-hover:hidden pl-[4px]"} hs-ui.svg.extra-hide/svg]
      [:span {:class "hidden group-hover:inline-block pl-[4px]"} hs-ui.svg.extra-hide-filled/svg]]
     [:<>
      [:span {:class "group-hover:text-[theme(colors.elements-readable)]"} (:slot/open props "More")]
      [:span {:class "group-hover:hidden pl-[4px]"} hs-ui.svg.extra-show/svg]
      [:span {:class "hidden group-hover:inline-block pl-[4px]"} hs-ui.svg.extra-show-filled/svg]])])
