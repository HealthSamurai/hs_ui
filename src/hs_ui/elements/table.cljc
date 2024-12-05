(ns hs-ui.elements.table
  (:require [hs-ui.utils :as utils]
            [hs-ui.text  :as text]))

(def root-class
  ["border-collapse"
   "table-fixed"
   "w-full"])

(def thead-class
  ["border-b"
   "border-[var(--color-separator)]"
   "text-[var(--color-elements-assistive)]"])

(def column-name-class
  ["p-4"
   "font-medium"
   "text-nowrap"
   "text-left"])

(def column-value-class
  ["px-4"
   "py-2"
   "whitespace-nowrap"
   "truncate"
   "break-all"
   ;;"max-w-[100px]"
   ;;"min-w-[100px]"
   ])

(def body-tr-class
  ["even:bg-[var(--color-surface-1)]"])

(defn colgroup [props]
  [:colgroup
   (for [column (:columns props)]
     [:col {:span  "1"
            :key   (utils/build-key ::colgroup column)
            :style {:width (:width column "auto")}}])])

(defn thead [props]
  [:thead {:class (utils/class-names thead-class (:c/thead-class props))}
   [:tr
    (for [column (:columns props)]
      [:th {:key   (utils/build-key ::head-col column)
            :class column-name-class
            :scope "col"}
       (:name column)])]])

(defn tbody [props]
  [:tbody
   (for [row (:items props)]
     [:tr {:key (utils/build-key ::row row) :class body-tr-class}
      (for [column (:columns props)]
        (let [column-name (:name column)]
          [:td {:key   (utils/build-key ::col column)
                :class column-value-class}
           [:span.truncate
            (or (get row column-name)
                (get row (keyword column-name)))]]))])])

(defn view [props]
  [:table {:class (utils/class-names root-class (:class props))}
   [colgroup props]
   [thead props]
   [tbody props]])
