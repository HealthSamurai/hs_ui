(ns hs-ui.components.checkbox-square
  (:require
   [hs-ui.svg.tick]
   [hs-ui.svg.minus-square]
   [hs-ui.svg.check-square-empty]))

(defn component
  [{:keys [checked not-all disabled on-change] :as props}]
  (let [icon        (cond
                      (and checked not-all)  hs-ui.svg.minus-square/svg
                       checked               hs-ui.svg.tick/svg
                       :else       [:div {:class "text-[#717684]"}
                                    hs-ui.svg.check-square-empty/svg])]

    [:label.inline-flex.items-center.cursor-pointer
     {:style (when disabled {:cursor "not-allowed" :opacity 0.5})
      :class "text-[var(--color-cta)]"
      :on-click  (fn [e]
                   (.stopPropagation e))}
     [:input {:type      "checkbox"
              :class     "sr-only"
              :checked   checked
              :disabled  disabled
              :on-change (fn [e]
                           (when (and on-change (not disabled))
                             (on-change e (.. e -target -checked))))}]
     icon]))
