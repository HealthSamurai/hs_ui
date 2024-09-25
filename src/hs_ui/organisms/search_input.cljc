(ns hs-ui.organisms.search-input
  (:require [hs-ui.components.input]
            [hs-ui.svg.search]
            [hs-ui.utils :as u]))

(defn component
  [props]
  [hs-ui.components.input/component
   (assoc props
          :c/root-class (u/class-names ["h-[32px]" (when (= "rounded" (:c/variant props))
                                                     "rounded-corner-max")]
                                       (:c/root-class props))
          :slot/left [:span {:class "text-[#CCCED3]"} hs-ui.svg.search/svg])])
