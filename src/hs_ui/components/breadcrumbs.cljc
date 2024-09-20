(ns hs-ui.components.breadcrumbs
  (:require [hs-ui.svg.nav-direction]
            [hs-ui.utils :as utils]))

(def root-class
  ["txt-body"
   "text-[theme(colors.cta)]"
   "flex"
   "items-center"
   ;; Hovered
   "hover:underline"
   "hover:underline-offset-[3px]"
   "data-[hovered=true]:underline"
   "data-[hovered=true]:underline-offset-[3px]"
   ])

(defn component
  [props]
  [:a (utils/merge-props {:class root-class} props)
   hs-ui.svg.nav-direction/svg
   (:body props)])
