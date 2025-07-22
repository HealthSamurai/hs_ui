(ns hs-ui.elements.breadcrumb
  (:require [hs-ui.components.tooltip]))

(defn item-view [{:keys [title href]}]
  [:a {:class "bg-[#F4F5F6] text-[#717684] txt-value rounded-[6px] flex items-center gap-[8px] px-2 py-[2px]"
       :href  href}
   title])

(defn title-view [{:keys [title]}]
  [hs-ui.components.tooltip/component
   {:place "bottom" :tooltip title}
   [:span {:class "text-gray-900 font-medium text-[20px] text-[var(--color-elements-readable)] leading-6"}
    title]])

(defn separator []
  [:span {:class "text-[#BBBBBB]"} "/"])

(defn view [items]
  (let [last-index (dec (count items))]
    [:div {:class "flex items-center space-x-2 text-nowrap"}
     (mapcat
      (fn [[idx item]]
        (let [is-last?  (= idx last-index)
              component (if is-last? title-view item-view)]
          (if is-last?
            [^{:key (str "breadcrumb-item-" idx)} [component item]]
            [^{:key (str "breadcrumb-item-" idx)} [component item]
             ^{:key (str "breadcrumb-sepa-" idx)} [separator]])))
      (map-indexed vector items))]))
