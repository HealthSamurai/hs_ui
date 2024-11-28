(ns hs-ui.components.textarea
  (:require
   [hs-ui.svg.area-scale]
   [hs-ui.utils]))

(def root-class
  ["group/textarea"
   "relative"])

(def fieldset-class
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
   "resize-none"
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
  [:div {:class (hs-ui.utils/class-names root-class (:c/root-class properties))}
   (when-let [slot-left (:slot/left properties)]
     [:div {:class slot-left-class} slot-left])
   [:fieldset {:class        fieldset-class
               :data-invalid (:data-invalid properties)
               :disabled     (:disabled properties)}
    [:textarea (hs-ui.utils/merge-props {:class input-class
                                         :rows  4
                                         :spellCheck false}
                                        properties)]]
   (when (or (:slot/right properties)
             (:data-invalid properties))
     [:div {:class slot-right-class}
      (:slot/right properties)
      (when (:data-invalid properties)
        [:span {:class "pr-[2px]"} hs-ui.svg.warning/svg])])])
