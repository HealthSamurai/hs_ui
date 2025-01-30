(ns hs-ui.components.tab
  (:require [hs-ui.utils :as u]))

(def root-class
  ["txt-label"
   "text-[theme(colors.elements-assistive)]"
   "pt-[6px]"
   "pb-[5px]"
   "cursor-pointer"
   ;; Hovered
   "hover:text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:text-[theme(colors.elements-readable)]"
   "border-b"
   "border-transparent"

   ;; Selected
   "has-[:checked]:border-[#EA4A35]"
   "has-[:checked]:text-[theme(colors.elements-readable)]"
   ])

(defn component
  "Possible component-specific props:
  :id
  :name
  :c/selected?
  :checked
  :on-change
  :slot/content"
  [props]
  [(if (:href props) :a :label) (dissoc (u/merge-props  {:class root-class} props)
                                        :on-change :name)
   [:input.hidden {:type           "radio"
                   :value          (:id props)
                   :name           (:name props)
                   :checked        (boolean (:c/selected? props))
                   :on-change      (:on-change props)}]
   (:slot/content props)])
