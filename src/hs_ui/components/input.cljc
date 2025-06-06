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
   "disabled:bg-[theme(colors.surface-1)]"])

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
   "disabled:cursor-not-allowed"])

(def slot-left-class
  ["flex"
   "h-full"
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
  "Properties:
  :c/root-class
  :c/slot-left-class
  :c/slot-right-class
  :c/input-tooltip
  :data-invalid
  :disabled
  :type
  :slot/right
  :c/error-message
  :slot/suggestions (an array of :value+:description maps)"
  [properties]
  [:fieldset {:class        (utils/class-names root-class (:c/root-class properties))
              :data-invalid (:data-invalid properties)
              :disabled     (:disabled properties)}
   (when-let [slot-left (:slot/left properties)]
     [:div {:class (utils/class-names slot-left-class (:c/slot-left-class properties))} slot-left])

   (let [tooltip-props (:c/input-tooltip properties)
         suggestions (:slot/suggestions properties)
         datalist-id (when suggestions (random-uuid))
         input [:input (utils/merge-props {:class input-class
                                           :spellCheck false
                                           :list datalist-id
                                           :onWheel   (when (= "number" (:type properties))
                                                        (fn [e]
                                                          (.blur (.-target e))))}
                                          properties)]]
     [:<>
      (when suggestions
        [:datalist {:id datalist-id}
         (for [{:keys [value description]} suggestions]
           [:option {:value (or value description)} description])])
      (if tooltip-props
        [hs-ui.components.tooltip/component (assoc tooltip-props :c/root-class "w-full") input]
        input)])

   (when (or (:slot/right properties)
             (:data-invalid properties))
     [:div {:class (utils/class-names slot-right-class (:c/slot-right-class properties))}
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
