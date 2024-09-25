(ns hs-ui.layout)

(defn confirmation
  [props]
  [:div {:class "border-t border-separator flex justify-end py-x2 px-x4"}
   (into [:div.space-x-x1] (:slot/right props))])
