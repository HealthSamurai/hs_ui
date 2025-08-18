(ns hs-ui.svg.floppy-disk)

(def svg
  [:svg
   {:xmlns "http://www.w3.org/2000/svg"
    :width "16"
    :height "16"
    :viewBox "0 0 64 64"
    :fill "none"}
   [:g {:stroke "currentColor" :stroke-width "3" :stroke-linecap "round" :stroke-linejoin "round"}
    [:rect {:x "27.27" :y "6.04" :width "8" :height "10" :fill "currentColor"}]

    [:line {:x1 "21" :y1 "40.04" :x2 "41" :y2 "40.04"}]
    [:line {:x1 "21" :y1 "48.04" :x2 "41" :y2 "48.04"}]

    [:rect {:x "14" :y "32.04" :width "34" :height "22"}]

    [:path
     {:d "M49.38,0.04H7c-2.21,0-4,1.79-4,4v56c0,2.21,1.79,4,4,4h50c2.21,0,4-1.79,4-4V11.7L49.38,0.04z
        M39.96,2.08v17.92H14.04V2.08h25.92z
        M59,60c0,1.1-0.9,2-2,2H7c-1.1,0-2-0.9-2-2V4c0-1.1,0.9-2,2-2h5v20.04h30V2h6.51L59,12.52V60z"}]]])
