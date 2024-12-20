(ns hs-ui.elements.table
  (:require
   [hs-ui.utils :as u]
   [hs-ui.svg.plus :as plus-icon]
   [hs-ui.svg.minus :as minus-icon]))

(def root-class
  ["table-fixed"
   "w-full"
   "border-spacing-0"
   "border-separate"])

(def thead-class
  ["text-[var(--color-elements-assistive)]"])

(def column-name-class
  ["p-4"
   "font-medium"
   "text-nowrap"
   "text-left"
   "bg-[var(--color-surface-0)]"
   "border-b"
   "border-[var(--color-separator)]"

   "sticky"
   "top-0"
   "z-10"])

(def table-row-class
  ["even:bg-[var(--color-surface-1)]"
   "aria-selected:bg-[var(--color-surface-selected)]"
   "data-[role=link]:cursor-pointer"
   "hover:data-[role=link]:bg-[var(--color-surface-1)]"
   "group/row"])

(def text-class
  ["truncate"
   "group-hover/row:opacity-40"
   "group-hover/cell:opacity-100"])

(def table-cell-class
  ["px-4"
   "py-2"
   "whitespace-nowrap"
   "relative"
   "break-all"
   "hover:shadow-[inset_0_0_0_1px_var(--color-cta)]"
   "group/cell"])

(def cell-toolbar-class
  ["absolute"
   "-top-6"
   "left-0"
   "px-1.5"
   "py-1"
   "gap-1.5"
   "bg-[var(--color-cta)]"
   "rounded-t"
   "z-10"
   "hidden"
   "group-hover/cell:flex"])

(def cell-toolbar-icon-class
  ["opacity-50"
   "text-[theme(colors.elements-readable-inv)]"
   "hover:opacity-100"])

(defn colgroup [props]
  [:colgroup
   (for [column (:columns props)]
     [:col {:key   (u/key ::colgroup column)
            :style {:width (:width column "auto")}}])])

(defn thead [props]
  [:thead {:class thead-class}
   [:tr
    (for [column (:columns props)]
      [:th {:class column-name-class :key (u/key ::head-col column)}
       (:name column)])]])

(defn cell-toolbar []
  [:div {:class cell-toolbar-class}
   [:span {:class cell-toolbar-icon-class} plus-icon/svg]
   [:span {:class cell-toolbar-icon-class} minus-icon/svg]])

(defn tbody [props]
  [:tbody
   (for [row (:rows props)]
     (let [on-row-click (:on-row-click props)]
       [:tr {:key           (u/key ::row row)
             :class         table-row-class
             :aria-selected (:selected? row)
             :data-role     (when on-row-click "link")
             :on-click      (when on-row-click #(on-row-click row))}
        (for [col (:columns props)]
          (let [value (or (get row (:name col))
                          (get row (keyword (:name col))))]
            [:td {:class table-cell-class
                  :title (or (:title value) (str (:value value)))
                  :key (u/key ::col col)}
             [:span {:class text-class}
              (:value value)]
             [cell-toolbar]]))]))])

(defn view [props]
  [:table {:class (u/class-names root-class (:class props))}
   [colgroup props]
   [thead props]
   [tbody props]])
