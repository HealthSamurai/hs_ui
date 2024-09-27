(ns hs-ui.organisms.dropdown
  (:require [hs-ui.organisms.search-input]
            [hs-ui.components.list-items]
            [hs-ui.components.list-item]
            [hs-ui.svg.chevron-down]
            [hs-ui.text]
            [hs-ui.layout]
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
   "bg-[theme(colors.surface-0)]"
   ])

(def disabled-class
  ["cursor-not-allowed"
   "bg-[theme(colors.surface-1)]"
   "text-elements-assistive"])

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
  [props selected-item]
  [:div {:class (cond-> selected-item-class
                  (:disabled props)
                  (hs-ui.utils/class-names disabled-class))
         :on-click (when-not (:disabled props) (:c/on-open props))}
   [:div.flex.items-center
    [:span {:class selected-item-slot-left} (:slot/left selected-item)]
    [hs-ui.text/value {:class (when (:disabled props) "text-elements-assistive")}
     (:slot/label selected-item)]]
   [:span {:class "text-[#727885]"} hs-ui.svg.chevron-down/svg]])

(defn get-selected-option
  [options value]
  (first (filter #(= value (:value %)) options)))

(defn element
  [props]
  (let [open?         (:c/open? props)
        selected-item (get-selected-option (:c/options props) (:value props))]
    [:div {:class (hs-ui.utils/class-names "relative" (:class props))}
     [selected-item-view props selected-item]
     (when open?
       [:div {:class menu-class}
        [hs-ui.organisms.search-input/component
         {:c/root-class search-input-class
          :ref          #(when % (.focus %))
          :on-change    (:c/on-search props)
          :on-blur      (fn [e]
                          (when-let [on-close (:c/on-close props)]
                            (on-close e)))
          :placeholder  "Search"}]
        [hs-ui.components.list-items/component {:class list-items-class}
         (for [option (:c/options props)]
           [hs-ui.components.list-item/component
            (merge option {:data-selected (= (:value selected-item) (:value option))})
            (:slot/left option)
            (:slot/label option)])]])]))


(defn component
  [props]
  [hs-ui.layout/control
   {:slot/control         [element props]
    :slot/label           (:slot/label props)
    :slot/assistive       (:slot/assistive props)
    :slot/assistive-right (when (contains? props :c/expand?)
                            [hs-ui.components.content-expand/component
                             {:c/open?  (:c/expand? props)
                              :class    (:c/expand-class props)
                              :on-click (:c/on-expand props)}])}])
