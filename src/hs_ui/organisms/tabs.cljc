(ns hs-ui.organisms.tabs
  (:require
   [hs-ui.utils :as u]
   [hs-ui.components.tab]))

(def root-class
  ["flex"
   "gap-x-[4px]"])

(defn component
  "Accepts a vector of maps (:c/items), each map might have
  :id
  :name
  :c/selected?
  :checked
  :on-change
  :slot/content"
  [props]
  [:div (u/merge-props {:class root-class} props)
   (for [item (:c/items props)] ^{:key (:id item)}
     [hs-ui.components.tab/component (assoc item :name (:name props))])])

(defn secondary-tab-item [{:keys [id label active? on-click class]}]
  [:div {:key id
         :on-click on-click
<<<<<<< Updated upstream
         :class ["px-[12px] py-[6px] leading-[15px] cursor-pointer border shrink-0"
||||||| Stash base
         :class ["px-[12px] py-[6px] leading-[15px] cursor-pointer border"
=======
         :data-active active?
         :class ["px-[12px] py-[6px] leading-[15px] cursor-pointer border"
>>>>>>> Stashed changes
                 (if active?
                   "text-[var(--color-elements-readable)] rounded-[6px] bg-[var(--color-surface-0)]"
                   "border-transparent")
                 class]}
   label])

(defn secondary-tabs [{:keys [class items]}]
<<<<<<< Updated upstream
  [:nav {:class (u/class-names "bg-[#F9F9F9] py-[6px] flex font-medium text-[var(--color-elements-assistive)] overflow-x-auto" class)}
||||||| Stash base
  [:nav {:class (u/class-names "bg-[#F9F9F9] py-[6px] flex font-medium text-[var(--color-elements-assistive)]" class)}
=======
  [:nav {:class (u/class-names "bg-[#F9F9F9] py-[6px] rounded-[6px] flex font-medium text-[var(--color-elements-assistive)]" class)}
>>>>>>> Stashed changes
   (for [item items]
     (secondary-tab-item item))])
