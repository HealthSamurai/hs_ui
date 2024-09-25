(ns hs-ui.organisms.dropdown
  (:require [hs-ui.organisms.search-input]
            [hs-ui.components.list-items]
            [hs-ui.components.list-item]
            [hs-ui.svg.chevron-down]
            [hs-ui.text]
            [hs-ui.utils]))

(def selected-item-class
  ["flex"
   "items-center"
   "justify-between"
   "cursor-pointer"
   "border"
   "border-border-default"
   "rounded-corner-m"
   "py-[5px]"
   "pl-x1point5"
   "pr-[11px]"
   "bg-surface-0"
   ])

(def selected-item-slot-left
  ["text-elements-assistive"
   "pr-[7px]"])

(def menu-class
  ["absolute"
   "w-full"
   "bg-surface-0"
   "border"
   "border-separator"
   "p-x1"
   "pt-[7px]"
   "rounded-corner-m"
   "mt-[4px]"
   "z-10"
   "shadow-dropdown"
   ])

(def list-items-class
  ["overflow-y-auto"
   "mb-x1"
   "pr-half"
   "max-h-[338px]"])

(def search-input-class
  ["mb-x1"
   "rounded-corner-s"])

(defn selected-item-view
  [selected-item]
  [:div {:class selected-item-class}
   [:div.flex.items-center
    [:span {:class selected-item-slot-left} (:slot/left selected-item)]
    [hs-ui.text/value {} (:slot/label selected-item)]]
   [:span {:class "text-[#727885]"} hs-ui.svg.chevron-down/svg]])

(defn get-selected-option
  [options value]
  (first (filter #(= value (:value %)) options)))

(defn component
  [props]
  (let [open?         (:c/open? props)
        selected-item (get-selected-option (:c/options props) (:value props))]
    [:<>
     (when (:slot/label props)
       [hs-ui.text/label {:class "pb-[11px]"} (:slot/label props)])
     [:div {:class (hs-ui.utils/class-names "relative" (:class props))}
      [selected-item-view selected-item]
      (when open?
        [:div {:class menu-class}
         [hs-ui.organisms.search-input/component
          {:c/root-class search-input-class
           :ref          #(when % (.focus %))
           :on-change    (:c/on-search props)
           :placeholder  "Search"}]
         [hs-ui.components.list-items/component {:class list-items-class}
          (for [option (:c/options props)]
            [hs-ui.components.list-item/component
             (merge option {:data-selected (= (:value selected-item) (:value option))})
             (:slot/left option)
             (:slot/label option)])]])]
     (when (:slot/assistive props)
       [hs-ui.text/assistive {:class "block pt-[12px]"} (:slot/assistive props)])]))