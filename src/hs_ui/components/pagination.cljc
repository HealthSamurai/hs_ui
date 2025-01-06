(ns hs-ui.components.pagination
  (:require
   [hs-ui.utils :as u]))

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
   "border-border-default"
   "rounded-corner-m"
   "bg-[theme(colors.surface-0)]"
   "py-[6px]"
   "px-[11px]"])

(def select-class
  ["w-full"
   "outline-none"
   "text-[theme(colors.elements-readable)]"
   "pr-6"
   "cursor-pointer"])

(def pagination-per-page-class
  ["flex"
   "items-center"
   "gap-2"])

(def page-selection-class
  ["flex"
   "items-center"
   "gap-2"])

(def page-item-class
  ["text-[theme(colors.elements-assistive)]"
   "hover:text-[theme(colors.elements-readable)]"
   "cursor-pointer"
   "border"
   "border-border-default"
   "rounded-corner-m"
   "bg-[theme(colors.surface-0)]"
   "py-[6px]"
   "px-[11px]"
   ;; “selected” state
   "has-[:checked]:text-[theme(colors.elements-readable)]"
   "has-[:checked]:bg-[theme(colors.surface-selected)]"])

(defn page-button
  [props]
  [:label {:class page-item-class
           :on-click (when (:on-click props)
                       #((:on-click props) (:id props)))}
   [:input.hidden {:type     "radio"
                   :checked  (:selected? props)
                   :on-change #(when (:on-click props)
                                 ((:on-click props) (:id props)))}]
   (:slot/content props)])


(defn results-per-page-select
  [props]
  (let [{:keys [options selected-value on-change]} props]
    [:div
     {:class "flex items-center gap-2"}
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
   Accepts:
   :page - current page number
   :total-pages - total number of pages
   :results-per-page - current results-per-page
   :on-page-change - callback for page changes
   :on-rpp-change  - callback for results-per-page changes"
  [props]
  (let [{:keys [page total-pages
                results-per-page]} props
        start (max 1 (- page 2))
        end   (min total-pages (+ page 2))
        page-range (range start (inc end))]

    [:div (u/merge-props {:class root-class} {:class (:class props)})
     [results-per-page-select
      {:options        [10 30 100]
       :selected-value results-per-page
       :on-change      (props :c/on-rpp-change)}]

     [:div {:class page-selection-class}
      [page-button {:id           :prev
                    :disabled?    (= page 1)
                    :on-click     (fn [_] (when (props :c/on-page-change)
                                            ((props :c/on-page-change) (dec page))))
                    :slot/content "‹"}]

      (for [p page-range]
        ^{:key p}
        [page-button
         {:id           p
          :selected?    (= p page)
          :on-click     (props :c/on-page-change)
          :slot/content (str p)}])

      [page-button {:id           :next
                    :disabled?    (= page total-pages)
                    :on-click     (fn [_] (when (props :c/on-page-change)
                                            ((props :c/on-page-change) (inc page))))
                    :slot/content "›"}]]]))
