(ns hs-ui.elements.table
  (:require
   [hs-ui.utils :as u]))

(def root-class
  ["table"
   "table-fixed"
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
  ["table-cell"
   "px-4"
   "py-2"
   "whitespace-nowrap"
   "relative"
   "break-all"])

(def body-tr-class
  ["table-row"
   "even:bg-[var(--color-surface-1)]"
   "aria-selected:bg-[var(--color-surface-selected)]"
   "data-[role=link]:cursor-pointer"
   "data-[role=link]:hover:opacity-40"])

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
             [:div {:style {:height "10px"
                            :width "10px"
                            :position "absolute"
                            :top "-10px"
                            :left "0px"
                            :gap "2px"
                            :inline-size "fit-content"
                            :padding-inline "2px"
                            :margin-block-end "-1px"
                            :background-color "rgb(0, 119, 204)"
                            :color "rgb(255, 255, 255)"
                            :border "1px solid rgb(0, 119, 204)"
                            :border-radius "3px"
                            :border-end-start-radius "0px"
                            :display "flex"
                            :z-index "1000"
                            :overflow "visible"}}]]))]))])

(defn view [props]
  [:table {:class (u/class-names root-class (:class props))}
   [colgroup props]
   [thead props]
   [tbody props]])
