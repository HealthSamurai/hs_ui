(ns hs-ui.components.content-expand
  (:require [hs-ui.svg.extra-hide]
            [hs-ui.svg.extra-hide-filled]
            [hs-ui.svg.extra-show]
            [hs-ui.svg.extra-show-filled]
            [hs-ui.utils]))

(def root-class
  ["inline-block"
   "w-fit"
   "relative"
   "contents"
   "flex"
   "items-center"
   "justify-between"
   "gap-half"
   "cursor-pointer"
   "group"
   "text-[theme(colors.elements-assistive)]"
   "txt-assistive"
   "select-none"])

(defn component
  [props]
  [:div (hs-ui.utils/merge-props {:class root-class}
                                 (dissoc props :open?))
   [:div {:class ["w-full flex items-center justify-between" (if (:c/open? props) "block absolute" "invisible")]}
    [:span {:class "group-hover:text-[theme(colors.elements-readable)] grow text-right"} (:slot/close props "Less")]
    [:span {:class "group-hover:hidden pl-[4px]"} hs-ui.svg.extra-hide/svg]
    [:span {:class "hidden group-hover:inline-block pl-[4px]"} hs-ui.svg.extra-hide-filled/svg]]
   [:div {:class ["w-full flex items-center justify-between" (if (:c/open? props) "invisible" "block absolute")]}
    [:span {:class "group-hover:text-[theme(colors.elements-readable)] grow text-right"} (:slot/open props "More")]
    [:span {:class "group-hover:hidden pl-[4px]"} hs-ui.svg.extra-show/svg]
    [:span {:class "hidden group-hover:inline-block pl-[4px]"} hs-ui.svg.extra-show-filled/svg]]])
