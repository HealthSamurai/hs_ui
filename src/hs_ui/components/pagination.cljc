(ns hs-ui.components.pagination
  (:require
   [hs-ui.utils :as u]
   [hs-ui.svg.chevron-down :as chevron-down]
   [hs-ui.components.tooltip]))

(def root-class
  ["flex"
   "justify-end"
   "items-center"
   "py-2"
   "px-4"
   "txt-label"
   "bg-[theme(colors.surface-0)]"])

(def select-wrapper-class
  ["flex"
   "items-center"
   "gap-2"
   "border"
   "border-[theme(colors.border-default)]"
   "rounded-corner-m"
   "bg-[theme(colors.surface-0)]"
   "py-[8px]"
   "px-[11px]"])

(def select-class
  ["w-full"
   "outline-none"
   "txt-value"
   "text-[theme(colors.elements-readable)]"
   "cursor-pointer"])

(def pagination-per-page-class
  ["flex"
   "items-center"
   "gap-[var(--spacing-x1point5)]"])

(def page-selection-class
  ["flex"
   "items-center"
   "gap-[var(--spacing-half)]"
   "mr-2"])

(def page-item-class
  ["flex"
   "justify-center"
   "text-[theme(colors.elements-assistive)]"
   "cursor-pointer"
   "rounded-corner-m"
   "bg-[theme(colors.surface-0)]"
   "px-3"
   "py-2"

   "has-[:checked]:text-[theme(colors.elements-readable)]"

   "hover:text-[theme(colors.elements-readable)]"
   "hover:bg-[theme(colors.surface-1)]"
   "hover:bg-[theme(colors.surface-1)]"

   "has-[:disabled]:bg-[theme(colors.surface-0)]"
   "has-[:disabled]:text-[theme(colors.elements-disabled)]"])

(defn page-button
  [props]
  [:label {:class page-item-class
           :on-click (if (and (:on-click props) (not (:disabled? props)))
                       (fn [_] ((:on-click props) (:id props)))
                       (fn [e] (.preventDefault e)))}
   [:input.hidden {:type     "radio"
                   :checked  (boolean (:selected? props))
                   :disabled (boolean (:disabled? props))
                   :on-change (fn [e] (.preventDefault e))}]
   (if (:c/tooltip-value props)
     [hs-ui.components.tooltip/component
      {:place "top"
       :tooltip [:pre (:c/tooltip-value props)]}
      (:slot/content props)]
     [:div (:slot/content props)])])

(defn results-per-page-select
  [props]
  (let [{:keys [options selected-value on-change]} props]
    [:div {:class pagination-per-page-class}
     [:div {:class select-wrapper-class}
      [:select {:class     select-class
                :value     selected-value
                :on-change (when on-change
                             #(on-change (.. % -target -value)))}
       (for [opt options]
         ^{:key {:value opt}}
         [:option {:value opt} (str opt " / page")])]]]))


(defn component
  "Complete pagination component.
   Props:
   :page - current page number
   :total-pages - total number of pages
   :results-per-page - current results-per-page
   :results-per-page-options options for results-per-page select
   :on-page-change - callback for page changes
   :on-first-page - callback for click on first page arrow
   :on-last-page - callback for click on last page arrow
   :on-rpp-change  - callback for results-per-page changes"
  [props]
  (let [{:keys [page total-pages
                results-per-page
                results-per-page-options]} props
        on-first-page (:c/on-first-page props)
        on-last-page (:c/on-last-page props)
        on-page-change (:c/on-page-change props)
        start (max 1 (cond (> page (- total-pages 3)) (- total-pages 4)
                           :else (- page 2)))
        end   (min total-pages (cond (> 4 page) 5
                                 :else (+ 2 page)))
        page-range (range start (inc end))]

    [:div (u/merge-props {:class root-class} {:class (:class props)})
     [:div {:class page-selection-class}
      (when on-page-change
        [page-button {:disabled?    (= page 1)
                      :on-click     (fn [_] (on-page-change (dec page)))
                      :slot/content [:div {:class "rotate-90"}
                                     chevron-down/svg]
                      :c/tooltip-value (when (not= page 1) "Previous page")}])
      (when (and on-first-page (> page 3) (> total-pages 5))
        [page-button {:on-click     (fn [_] (on-first-page))
                      :slot/content "1"}])

      (when (and (> page 4) (> total-pages 5))
        [page-button {:on-click     (fn [_] (on-page-change (- page 5)))
                      :slot/content "..."
                      :c/tooltip-value "Previous 5 pages"}])


      (for [p page-range]
        ^{:key p}
        [page-button
         {:id           p
          :selected?    (= p page)
          :on-click     (props :c/on-page-change)
          :slot/content (str p)}])

      (when (and (< page (- total-pages 3)) (> total-pages 5))
        [page-button {:on-click     (fn [_] (on-page-change (min (+ page 5) total-pages)))
                        :slot/content "..."
                        :c/tooltip-value "Next 5 pages"}])

      (when (and on-last-page (< page (- total-pages 2)) (> total-pages 5))
        [page-button {:disabled?    (= page total-pages)
                      :on-click     (fn [_] (on-last-page))
                      :slot/content total-pages}])
      (when on-page-change
        [page-button {:disabled?    (= page total-pages)
                      :on-click     (fn [_] (on-page-change (inc page)))
                      :slot/content [:div {:class "rotate-[-90deg]"}
                                     chevron-down/svg]
                      :c/tooltip-value (when (not= page total-pages) "Next page")}])]

     [results-per-page-select
      {:options (if results-per-page-options results-per-page-options [10 30 100])
       :selected-value results-per-page
       :on-change      (props :c/on-rpp-change)}]]))
