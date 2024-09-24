(ns hs-ui.components.content-expand
  (:require [hs-ui.svg.extra-hide]
            [hs-ui.svg.extra-hide-filled]
            [hs-ui.svg.extra-show]
            [hs-ui.svg.extra-show-filled]
            [hs-ui.utils]))

(def root-class
  ["inline-block"
   "flex"
   "items-center"
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
   (if (:open? props)
     [:<>
      [:span {:class "group-hover:text-[theme(colors.elements-readable)]"} (:slot/close props "Less")]
      [:span {:class "group-hover:hidden"} hs-ui.svg.extra-hide/svg]
      [:span {:class "hidden group-hover:inline-block"} hs-ui.svg.extra-hide-filled/svg]]
     [:<>
      [:span {:class "group-hover:text-[theme(colors.elements-readable)]"} (:slot/open props "More")]
      [:span {:class "group-hover:hidden"} hs-ui.svg.extra-show/svg]
      [:span {:class "hidden group-hover:inline-block"} hs-ui.svg.extra-show-filled/svg]])])
