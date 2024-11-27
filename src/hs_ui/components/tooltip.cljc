(ns hs-ui.components.tooltip
  (:require [hs-ui.utils]
            #?(:cljs ["react-tooltip" :refer [Tooltip]]))
  )

(defn component [{:keys [place tooltip]} children]
  (let [reference-id (gensym "reference-id-")]
    [:<>
     [:div {:id reference-id} children]
     #?(:cljs
        [:> Tooltip {:anchorSelect (str "#" reference-id)
                     :place place
                     :disableStyleInjection true
                     }
         [:div {:class "text-[var(--color-elements-readable)] bg-[theme(colors.surface-1)] px-5 py-2 rounded  shadow-xl"}
          tooltip]])]))
