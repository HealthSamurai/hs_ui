(ns hs-ui.components.chip
  (:require
   [hs-ui.svg.close :as close-icon]
   [hs-ui.text :as text]
   [hs-ui.utils :as utils]))

(def root-class
  ["border"
   "border-border-default"
   "rounded-sm"
   "px-3"
   "py-1"
   "inline-flex"
   "items-center"
   "gap-2"
   "text-nowrap"])

(defn component
  "Properties:
  :c/root-class
  :key
  :value
  :negative?
  :valid?
  :on-delete-click"
  [props]
  [:div {:class (utils/class-names root-class (:c/root-class props))}
   [:span {:class "txt-value" :title (when-not (:valid? props) "Invalid property")}
    (when (:negative? props)
      [:span {:class "font-semibold text-[theme(colors.illustrations-solid)]"}
       "NOT "])
    [:span {:class (if (:valid? props)
                     "text-[theme(colors.cta)]"
                     "text-[theme(colors.illustrations-solid)]")}
     (:key props)]
    [:span {:class "text-[theme(colors.elements-assistive)]"}
     "="]
    [:span {:class "text-[theme(colors.elements-readable)]"}
     (:value props)]]
   [:span {:class "cursor-pointer text-[theme(colors.elements-assistive)]"
           :on-click (:on-delete-click props)}
    close-icon/svg]])
