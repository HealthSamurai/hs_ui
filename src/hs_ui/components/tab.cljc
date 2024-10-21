(ns hs-ui.components.tab
  (:require [hs-ui.utils :as u]))

(def root-class
  ["txt-label"
   "w-fit"
   "text-[theme(colors.elements-assistive)]"
   "pb-[4.5px]"
   ;; Hovered
   "hover:text-[theme(colors.elements-readable)]"
   "data-[hovered=true]:text-[theme(colors.elements-readable)]"

   ;; Selected
   "has-[:checked]:border-b"
   "has-[:checked]:border-[#EA4A35]"
   "has-[:checked]:text-[theme(colors.elements-readable)]"
   "has-[:checked]:pb-[3.5px]"
   ])

(defn component
  [props]
  [:label.cursor-pointer (dissoc (u/merge-props  {:class root-class} props)
                                 :on-change :id :name)
   [:input.hidden {:type           "radio"
                   :value          (:id props)
                   :name           (:name props)
                   :checked        (:c/selected? props)
                   :on-change      (:on-change props)}]
   (:slot/content props)])
