(ns hs-ui.components.input
  (:require [hs-ui.utils :as utils]
            [hs-ui.text]
            [hs-ui.components.button]
            [hs-ui.svg.warning]))

(def root-class
  ["border"
   "border-[theme(colors.border-default)]"
   "rounded-[theme(borderRadius.corner-m)]"
   "focus-visible:outline"
   "flex"
   "items-center"
   "shadow-sm"
   "h-[36px]"
   ;; Invalid
   "data-[invalid=true]:border-[theme(colors.critical-default)]"
   ;; Disabled
   "disabled:bg-[theme(colors.surface-1)]"
   ])

(def input-class
  ["outline-none"
   "txt-value"
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
   "pl-[11px]"
   ])

(def slot-right-class
  ["flex"
   "items-center"
   "space-x-[theme(spacing.x1)]"
   "pr-[11px]"
   ])


(defn component
  [properties]
  (let [input-ref (utils/ratom nil)]
    [:fieldset {:class        (utils/class-names root-class (:c/root-class properties))
                :data-invalid (:data-invalid properties)
                :disabled     (:disabled properties)
                :on-click    #(.focus @input-ref)}
     (when-let [slot-left (:slot/left properties)]
       [:div {:class slot-left-class} slot-left])
     [:input (utils/merge-props {:class input-class
                                 :spellCheck false
                                 :ref   #(when % (reset! input-ref %))}
                                properties)]
     (when (or (:slot/right properties)
               (:data-invalid properties))
       [:div {:class slot-right-class}
        (:slot/right properties)
        (when (:data-invalid properties) hs-ui.svg.warning/svg)])]))
