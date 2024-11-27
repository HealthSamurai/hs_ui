(ns hs-ui.components.textarea
  (:require [hs-ui.utils]
            [hs-ui.svg.area-scale]))

(def root-class
  ["border"
   "border-[theme(colors.border-default)]"
   "rounded-[theme(borderRadius.corner-m)]"
   "focus-within:shadow-input"
   "focus-within:border-[theme(colors.border-XS-regular-hover)]"
   "flex"
   "items-center"
   ;; Invalid
   "data-[invalid=true]:border-[theme(colors.critical-default)]"
   ;; Disabled
   "disabled:bg-[theme(colors.surface-1)]"
   "relative"
   "p-[6px]"
   "pb-[8px]"
   ])

(def input-class
  ["outline-none"
   "txt-value"
   "text-[theme(colors.elements-readable)]"
   "px-[11px]"
   "py-[6px]"
   "bg-transparent"
   "w-full"
   "placeholder:text-[theme(colors.elements-disabled)]"
   ;; Invalid
   "data-[invalid=true]:text-[theme(colors.critical-default)]"
   ;; Disabled
   "disabled:text-[theme(colors.elements-assistive)]"
   ])

(def slot-left-class
  ["flex"
   "items-center"
   "space-x-[theme(spacing.x1)]"
   "pl-[12px]"
   ])

(def slot-right-class
  ["absolute"
   "right-[6px]"
   "top-[5px]"])


(defn component
  [properties]
  [:fieldset {:class        (hs-ui.utils/class-names root-class (:c/root-class properties))
              :data-invalid (:data-invalid properties)
              :disabled     (:disabled properties)}
   (when-let [slot-left (:slot/left properties)]
     [:div {:class slot-left-class} slot-left])
   [:textarea (hs-ui.utils/merge-props {:class input-class
                                        :rows  4
                                        :spellCheck false}
                                       properties)]
   [:div {:class "absolute bottom-[6px] right-[6px] pointer-events-none"}
    hs-ui.svg.area-scale/svg]
   (when (or (:slot/right properties)
             (:data-invalid properties))
     [:div {:class slot-right-class}
      (:slot/right properties)
      (when (:data-invalid properties)
        [:span {:class "pr-[2px]"} hs-ui.svg.warning/svg])])])
