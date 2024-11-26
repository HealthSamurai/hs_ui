(ns hs-ui.layout
  (:require [hs-ui.utils]
            [hs-ui.text]))

(defn confirmation
  [props]
  [:div (hs-ui.utils/merge-props {:class "border-t border-separator flex justify-end py-x2 px-[theme(spacing.x4)]"} props)
   (into [:div.space-x-x1] (:slot/right props))])

(defn nest
  [props]
  (let [show? (:c/show? props)]
    [:div (when show? {:class "outline outline-1 outline-separator rounded-corner-l"})
     [:div {:class ["px-x2" (if show? "py-x3" "pt-x3")]} (:slot/control props)]
     (when show?
       [confirmation {:slot/right (:slot/confirmation props) :class "px-[theme(spacing.x2)]"}])]))

(defn navbar
  [props]
  [:div {:class "flex border-b border-separator items-center h-[64px] justify-between w-full px-x3"}
   [:div (:slot/left props)]
   [:div (:slot/middle props)]
   [:div (:slot/right props)]])

(defn control
  [props]
  [:div (hs-ui.utils/merge-props {:class "pb-[12px]"} props)
   (when (:slot/label props)
     [hs-ui.text/label {:class "w-full pb-[11px]"} (:slot/label props)])
   (when (:c/assistive-top? props)
     [:div {:class "w-full flex justify-between pb-[12px]"}
      [hs-ui.text/assistive {:class (when (:data-invalid props) "text-critical-default")}
       (:slot/assistive props)]
      [:div (:slot/assistive-right props)]])
   (:slot/control props)
   (when-not (:c/assistive-top? props)
     (when (or (:slot/assistive props)
               (:slot/assistive-right props))
       [:div {:class "w-full flex gap-x1point5 justify-between pt-[12px]"}
        [hs-ui.text/assistive {:class (when (:data-invalid props) "text-critical-default")}
         (:slot/assistive props)]
        [:div (:slot/assistive-right props)]]))])

(defn expandeable-control
  [props]
  [:div
   (:slot/control props)
   [:div {:class ["w-full pb-x2" (if (:c/expand? props) "block" "hidden")]}
    (:slot/content props)]])
