(ns hs-ui.components.input
  (:require [hs-ui.utils :as utils]
            [hs-ui.text]
            [hs-ui.components.button]
            [hs-ui.svg.warning]
            [hs-ui.components.tooltip]))

(def root-class
  ["border"
   "border-[theme(colors.border-default)]"
   "rounded-[theme(borderRadius.corner-m)]"
   "focus-within:shadow-input"
   "focus-within:border-[theme(colors.border-XS-regular-hover)]"
   "flex"
   "items-center"
   "h-[36px]"
   ;; Invalid
   "data-[invalid=true]:border-[theme(colors.critical-default)]"
   ;; Disabled
   "disabled:bg-[theme(colors.surface-1)]"
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
  ["flex"
   "items-center"
   "space-x-[theme(spacing.x1)]"
   "pr-[12px]"])


(defn component
  [properties]
  [:fieldset {:class        (utils/class-names root-class (:c/root-class properties))
              :data-invalid (:data-invalid properties)
              :disabled     (:disabled properties)}
   (when-let [slot-left (:slot/left properties)]
     [:div {:class slot-left-class} slot-left])
   [:input (utils/merge-props {:class input-class
                               :spellCheck false
                               :onWheel   (when (= "number" (:type properties))
                                            (fn [e]
                                              (.blur (.-target e))))}
                              properties)]
   (when (or (:slot/right properties)
             (:data-invalid properties))
     [:div {:class slot-right-class}
      (:slot/right properties)
      (when (:data-invalid properties)
        (if (:c/error-message properties)
          [:span {:class "pr-[2px]"}
           (if-let [message (:c/error-message properties)]
             [hs-ui.components.tooltip/component {:place "top-start"
                                                  :error?  true
                                                  :tooltip message}
              hs-ui.svg.warning/svg]
             hs-ui.svg.warning/svg)]))])])
