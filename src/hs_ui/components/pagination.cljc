(ns hs-ui.components.pagination
  (:require
   [hs-ui.utils :as u]
   [hs-ui.svg.chevron-down :as chevron-down]
   [hs-ui.svg.chevron-flat :as chevron-flat]))

(def root-class
  ["flex"
   "justify-between"
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
   "gap-2"])

(def page-item-class
  ["text-[theme(colors.elements-assistive)]"
   "cursor-pointer"
   "border"
   "border-border-default"
   "rounded-corner-m"
   "bg-[theme(colors.surface-0)]"
   "py-[8px]"
   "px-[var(--spacing-x2)]"

   "has-[:checked]:text-[theme(colors.elements-readable)]"
   "has-[:checked]:bg-[theme(colors.surface-selected)]"

   "hover:text-[theme(colors.elements-readable)]"
   "hover:bg-[theme(colors.surface-selected)]"

   "has-[:disabled]:bg-[theme(colors.surface-0)]"
   "has-[:disabled]:text-[theme(colors.elements-disabled)]"])

(defn page-button
  [props]
  [:label {:class page-item-class
           :on-click (if (and (:on-click props) (not (:disabled? props)))
                       (fn [_] ((:on-click props) (:id props)))
                       (fn [e] (#?(:cljs (.preventDefault e) :clj nil))))}
   [:input.hidden {:type     "radio"
                   :checked  (:selected? props)
                   :disabled (:disabled? props)
                   :on-change (fn [e] (#?(:cljs (.preventDefault e) :clj nil)))}]
   (:slot/content props)])

(defn results-per-page-select
  [props]
  (let [{:keys [options selected-value on-change]} props]
    [:div {:class pagination-per-page-class}
     [:span {:class "text-[theme(colors.elements-readable)]"}
      "Results per page"]

     [:div {:class select-wrapper-class}
      [:select {:class     select-class
                :value     selected-value
                :on-change (when on-change
                             #?(:cljs #(on-change (.. % -target -value))
                                :clj nil))}
       (for [opt options]
         ^{:key {:value opt}}
         [:option {:value opt} (str opt)])]]]))


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
        start (max 1 (- page 2))
        end   (min total-pages (+ page 2))
        page-range (range start (inc end))]

    [:div (u/merge-props {:class root-class} {:class (:class props)})
     [results-per-page-select
      {:options (if results-per-page-options results-per-page-options [10 30 100])
       :selected-value results-per-page
       :on-change      (props :c/on-rpp-change)}]

     [:div {:class page-selection-class}
      (when on-first-page
        [page-button {:id           :prev
                      :disabled?    (= page 1)
                      :on-click     (fn [_] (on-first-page (dec page)))
                      :slot/content chevron-flat/svg}])
      (when on-page-change
        [page-button {:id           :prev
                      :disabled?    (= page 1)
                      :on-click     (fn [_] (on-page-change (dec page)))
                      :slot/content [:div {:class "rotate-90"}
                                     chevron-down/svg]}])

      (for [p page-range]
        ^{:key p}
        [page-button
         {:id           p
          :selected?    (= p page)
          :on-click     (props :c/on-page-change)
          :slot/content (str p)}])

      (when on-page-change
        [page-button {:id           :next
                      :disabled?    (= page total-pages)
                      :on-click     (fn [_] (on-page-change (inc page)))
                      :slot/content [:div {:class "rotate-[-90deg]"}
                                     chevron-down/svg]}])
      (when on-last-page
        [page-button {:id           :next
                      :disabled?    (= page total-pages)
                      :on-click     (fn [_] (on-last-page (inc page)))
                      :slot/content [:div {:class "rotate-180"}
                                     chevron-flat/svg]}])]]))
