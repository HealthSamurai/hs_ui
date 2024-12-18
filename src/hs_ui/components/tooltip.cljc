(ns hs-ui.components.tooltip
  (:require [hs-ui.utils]
            #?(:cljs ["react-tooltip" :refer [Tooltip]]))
  )

(defn component [{:keys [place tooltip error?]} children]
  (let [reference-id (gensym "reference-id-")]
    [:<>
     [:div {:id reference-id} children]
     #?(:cljs
        [:> Tooltip {:anchorSelect (str "#" reference-id)
                     :offset 3
                     :place place
                     :disableStyleInjection true}
         [:div {:class ["text-sm px-3 py-1 rounded shadow-xl"
                        (if error?
                          "text-white bg-[var(--color-critical-default)]"
                          "text-[var(--color-elements-readable)] bg-[theme(colors.surface-1)]")]}
          tooltip]])]))
