(ns hs-ui.svg.h-gripper)

(def svg
  [:svg {:xmlns "http://www.w3.org/2000/svg"
         :width "100%"
         :height "100%"
         :viewBox "0 0 24 24"
         :fill "currentColor"
         :stroke "currentColor"
         :stroke-linecap "round"
         :stroke-linejoin "round"}
   [:circle {:cx "5"  :cy "9" :r "1"}]
   [:circle {:cx "12" :cy "9" :r "1"}]
   [:circle {:cx "19" :cy "9" :r "1"}]
   [:circle {:cx "5"  :cy "15" :r "1"}]
   [:circle {:cx "12" :cy "15" :r "1"}]
   [:circle {:cx "19" :cy "15" :r "1"}]])
