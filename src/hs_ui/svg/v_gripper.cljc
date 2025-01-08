(ns hs-ui.svg.v-gripper)

(def svg
  [:svg {:xmlns "http://www.w3.org/2000/svg"
         :width "100%"
         :height "100%"
         :viewBox "0 0 24 24"
         :fill "currentColor"
         :stroke "currentColor"
         :stroke-linecap "round"
         :stroke-linejoin "round"}
   [:circle {:cx "9" :cy "5" :r "1"}]
   [:circle {:cx "9" :cy "12" :r "1"}]
   [:circle {:cx "9" :cy "19" :r "1"}]
   [:circle {:cx "15" :cy "5" :r "1"}]
   [:circle {:cx "15" :cy "12" :r "1"}]
   [:circle {:cx "15" :cy "19" :r "1"}]])
