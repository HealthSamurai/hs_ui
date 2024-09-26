(ns hs-ui.layout
  (:require [hs-ui.utils]
            [hs-ui.text]))

(defn confirmation
  [props]
  [:div (hs-ui.utils/merge-props {:class "border-t border-separator flex justify-end py-x2 px-[theme(spacing.x4)]"} props)
   (into [:div.space-x-x1] (:slot/right props))])

(defn nest
  [props]
  (if (:c/show? props)
    [:div {:class "outline outline-1 outline-separator rounded-corner-l"}
     [:div {:class "py-x3 px-x2"} (:slot/control props)]
     [confirmation {:slot/right (:slot/confirmation props) :class "px-[theme(spacing.x2)]"}]]
    [:div {:class "py-x3 px-x2"} (:slot/control props)]))

(defn navbar
  [props]
  [:div {:class "flex border-b border-separator items-center h-[64px] justify-between w-full px-x3"}
   [:div (:slot/left props)]
   [:div (:slot/middle props)]
   [:div (:slot/right props)]])

(defn control
  [props]
  [:<>
   [hs-ui.text/label {:class "pb-[11px]"} (:slot/label props)]
   (:slot/control props)
   [:div {:class "w-full flex justify-between py-[12px]"}
    [hs-ui.text/assistive {:class (when (:data-invalid props) "text-critical-default")}
     (:slot/assistive props)]
    [:div (:slot/assistive-right props)]]])

(defn expandeable-control
  [props]
  [:div
   (:slot/control props)
   (when (:c/expand? props)
     [:div {:class "w-full px-x1point5"}
      (:slot/content props)])])
