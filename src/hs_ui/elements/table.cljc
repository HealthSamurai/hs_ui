(ns hs-ui.elements.table
  (:require
   [hs-ui.utils :as u]
   [hs-ui.components.button :as button]
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

(def column-value-class
  ["px-4"
   "py-2"
   "whitespace-nowrap"
   "relative"
   "break-all"
   "hover:shadow-[inset_0_0_0_1px_#2278e1]"
   "group"])

(def body-tr-class
  ["table-row"
   "even:bg-[var(--color-surface-1)]"
   "aria-selected:bg-[var(--color-surface-selected)]"
   "data-[role=link]:cursor-pointer"
   "data-[role=link]:hover:opacity-40"])

(def action-bar-class
  ["absolute"
   "h-6"
   "w-[50px]"
   "-top-6"
   "left-0"
   "border"
   "border-solid"
   "border-[#2278E1]"
   "bg-[#2278E1]"
   "rounded-t"
   "z-10"
   "py-1"
   "px-1.5"
   "hidden"
   "group-hover:flex"])

(def action-bar-button-class
  ["opacity-50"
   "text-[theme(colors.elements-readable-inv)]"
   "[&_svg]:enabled:text-[theme(elements-readable-inv)]"
   "hover:text-[theme(colors.elements-readable-inv)]"
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

(defn action-bar []
  [:div {:class action-bar-class}
   [button/slim
    {:class action-bar-button-class}
    plus-icon/svg]
   [button/slim
    {:class action-bar-button-class}
    minus-icon/svg]])

(defn tbody [props]
  [:tbody
   (for [row (:rows props)]
     (let [on-row-click (:on-row-click props)]
       [:tr {:key           (u/key ::row row)
             :class         body-tr-class
             :aria-selected (:selected? row)
             :data-role     (when on-row-click "link")
             :on-click      (when on-row-click #(on-row-click row))}
        (for [col (:columns props)]
          (let [value (or (get row (:name col))
                          (get row (keyword (:name col))))]
            [:td {:class column-value-class
                  :title (or (:title value) (str (:value value)))
                  :key (u/key ::col col)}
             [:span {:class ["truncate"]}
              (:value value)]
             [action-bar]]))]))])

(defn view [props]
  [:table {:class (u/class-names root-class (:class props))}
   [colgroup props]
   [thead props]
   [tbody props]])
