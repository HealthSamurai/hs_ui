(ns hs-ui.organisms.search-input
  (:require [hs-ui.components.input]
            [hs-ui.svg.search]
            [hs-ui.svg.close]
            [hs-ui.utils :as u]))

(defn component
  [props]
  [hs-ui.components.input/component
   (cond-> (assoc props
                  :c/root-class (u/class-names ["h-[32px]" (when (= "rounded" (:c/variant props))
                                                             "rounded-corner-max")]
                                               (:c/root-class props))
                  :slot/left [:span {:class "text-[#CCCED3]"} hs-ui.svg.search/svg])

     (some? (:c/on-clean props))
     (assoc :slot/right [:span {:class ["text-[#CCCED3] cursor-pointer invisible" (when (seq (str (:value props)))
                                                                                    "!visible")]
                                :on-click (:c/on-clean props)}
                         hs-ui.svg.close/svg]))])
