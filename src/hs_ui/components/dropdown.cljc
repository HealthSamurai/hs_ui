(ns hs-ui.components.dropdown
  (:require [hs-ui.utils :as utils]
            [hs-ui.svg.search]
            #?(:cljs ["@ariakit/react" :as kit])))

(def temp-test-svg
  [:svg {:xmlns "http://www.w3.org/2000/svg"
         :width "14"
         :height "15"
         :viewBox "0 0 14 15"
         :fill "none"}
   [:path {:d "M12.3335 1.5H9.00016C8.63197 1.5 8.3335 1.79848 8.3335 2.16667V5.5C8.3335 5.86819 8.63197 6.16667 9.00016 6.16667H12.3335C12.7017 6.16667 13.0002 5.86819 13.0002 5.5V2.16667C13.0002 1.79848 12.7017 1.5 12.3335 1.5Z"
           :stroke "currentColor"
           :stroke-width "1.25"
           :stroke-linecap "round"
           :stroke-linejoin "round"}]
   [:path {:d "M5.66667 13.5001V4.83341C5.66667 4.6566 5.59643 4.48703 5.4714 4.36201C5.34638 4.23699 5.17681 4.16675 5 4.16675H1.66667C1.48986 4.16675 1.32029 4.23699 1.19526 4.36201C1.07024 4.48703 1 4.6566 1 4.83341V12.8334C1 13.0102 1.07024 13.1798 1.19526 13.3048C1.32029 13.4298 1.48986 13.5001 1.66667 13.5001H9.66667C9.84348 13.5001 10.013 13.4298 10.1381 13.3048C10.2631 13.1798 10.3333 13.0102 10.3333 12.8334V9.50008C10.3333 9.32327 10.2631 9.1537 10.1381 9.02868C10.013 8.90365 9.84348 8.83341 9.66667 8.83341H1"
           :stroke "currentColor"
           :stroke-width "1.25"
           :stroke-linecap "round"
           :stroke-linejoin "round"}]])

(defn combobox-items
  [selected-value props]
  [:> #?(:cljs kit/ComboboxItem)
   (-> (assoc props
              :className
              (cond-> "bg-white h-[32px] py-[4px] hover:bg-listItem-hovered rounded-m flex items-center px-[15px] py-[8px] z-20"
                (= @selected-value (:value props))
                (utils/class-names "bg-[theme(backgroundColor.listItem-selected)] [&_svg]:text-[theme(textColor.elements-readable)]")
                (not (= @selected-value (:value props)))
                (utils/class-names "[&_svg]:text-[#90959F]")))
       (dissoc :label))
   [:span {:class "mr-[8px]"} temp-test-svg]
   (:label props)])

(def test-items
  [{:value "1" :label "Account"}
   {:value "2" :label "ActivityDefinition"}
   {:value "3" :label "AdverseEvent"}
   {:value "4" :label "AllergyIntolerance"}
   {:value "5" :label "ActivityAppointment"}
   {:value "6" :label "MedicinalProductUndesirableEffect"}])

(defn component
  [user-properties & children]
  (let [selected-value (utils/ratom (:value user-properties))]
    (fn [user-properties & children]
      [:> #?(:cljs kit/ComboboxProvider)
       {:setValue (fn [value] (reset! selected-value value))}
       [:div.relative
        [:> #?(:cljs kit/Combobox)
         {:className "border h-[32px] border-1 border-border-default rounded-m px-[12px] pl-[36px] py-[5.5px] focus:outline-none placeholder:text-[#CCCED3] z-10 w-full group"
          :placeholder " Search"}]
        (when @selected-value
          [:> #?(:cljs kit/ComboboxDisclosure)
           {:className "group-[aria-expanded]:bg-red-500 absolute top-0 left-0 w-full z-10"}
           [combobox-items selected-value (first (filter #(= @selected-value (:value %)) test-items))]])
        [:div.absolute.top-0.left-0 {:class "mt-[8px] ml-[12px]"} hs-ui.svg.search/svg]]
       [:> #?(:cljs kit/ComboboxPopover)
        {:className "bg-white border border-1 border-border-default rounded-m mt-[-40px] pt-[48px] pb-[10px] z-[-1] mx-[-7px] h-max-[200px] shadow-combobox-popover"
         :sameWidth true}
        (for [item test-items] ^{:key (:value item)}
          [:div {:class "mx-[6px]"}
           [combobox-items selected-value item]])]])))