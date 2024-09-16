(ns hs-ui.components.content-expand
  (:require [hs-ui.svg.extra-hide]
            [hs-ui.svg.extra-hide-filled]
            [hs-ui.svg.extra-show]
            [hs-ui.svg.extra-show-filled]
            [hs-ui.utils]))

(defn component
  [props]
  [:div (hs-ui.utils/merge-props {:class "inline-block flex items-center gap-half cursor-pointer group text-[theme(colors.color-elements-assistive)] text-assistive select-none"}
                                 (dissoc props :open? :open-text :close-text))
   (if (:open? props)
     [:<>
      [:span {:class "group-hover:text-[theme(colors.color-elements-readable)]"} (:close-text props "Less")]
      [:span {:class "group-hover:hidden"} hs-ui.svg.extra-hide/svg]
      [:span {:class "hidden group-hover:inline-block"} hs-ui.svg.extra-hide-filled/svg]]
     [:<>
      [:span {:class "group-hover:text-[theme(colors.color-elements-readable)]"} (:open-text props "More")]
      [:span {:class "group-hover:hidden"} hs-ui.svg.extra-show/svg]
      [:span {:class "hidden group-hover:inline-block"} hs-ui.svg.extra-show-filled/svg]])])
