(ns hs-ui.components.input
  (:require [hs-ui.utils :as utils]
            [hs-ui.text]
            [hs-ui.components.button]
            [hs-ui.svg.warning]))

(def base-class
  ["h-[36px]"
   "txt-value"
   "border"
   "outline-none"
   "border-[theme(colors.border-default)]"
   "rounded-[theme(borderRadius.corner-m)]"
   "bg-[theme(colors.surface-0)]"
   "px-[11px]"
   "placeholder:text-[theme(colors.elements-disabled)]"
   "placeholder:txt-value"
   ;; Disabled
   "disabled:bg-[theme(colors.surface-1)]"
   "disabled:text-[theme(colors.elements-assistive)]"
   ;; Invalid
   "data-[invalid=true]:border-[theme(colors.critical-default)]"
   "data-[invalid=true]:text-[theme(colors.critical-default)]"])

(defn component
  [properties]
  [:fieldset {:class "relative w-fit"}
   (when-let [slot-left (:slot/left properties)]
     [:div {:class "absolute top-[10px] left-[11px]"}
      [:div {:class "flex items-center space-x-[theme(spacing.x1)] bg-[theme(colors.surface-0)]"}
       slot-left]])
   [:input (utils/merge-props {:class (cond-> base-class (:slot/left properties) (conj "pl-[35px]"))} properties)]
   [:div {:class "absolute top-[6px] right-[11px]"}
    [:div {:class "flex items-center pl-[11.5px] space-x-[theme(spacing.x1)] bg-[theme(colors.surface-0)]"}
     (:slot/right properties)
     (when (:data-invalid properties) hs-ui.svg.warning/svg)]]])
