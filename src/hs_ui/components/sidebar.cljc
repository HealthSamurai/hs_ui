(ns hs-ui.components.sidebar
  (:require
   [hs-ui.utils :as utils]
   [hs-ui.svg.chevron-right]))

(def root-class
  ["w-[240px]"
   "h-full"
   "flex"
   "text-[theme(colors.elements-readable)]"
   "bg-white"
   "font-normal"
   "[border-right:1px_solid_#dbdde3]"
   "[flex-flow:column]"
   "[line-height:20px]"
   "[font-size:14px]"])

(def submenu-class
  ["p-1"
   "pb-0"
   "mb-4"
   "[border-top:1px_solid_#dbdde3]"])

(def header-class
  ["flex"
   "items-center"
   "space-x-1"
   "py-1"])

(def content-items-class
  ["w-full"
   "space-y-[2px]"
   "[&_details]:w-full"
   "[&.root>li:last-child]:mb-[100px]"
   "[&_a]:hover:text-inherit"
   "[&_li_a]:pl-[12px]"
   "[&_li_li_a]:ml-[8px]"
   "[&_li_li_a]:pl-[8px]"
   "[&_li_li_li_a]:pl-[64px]"
   "[&_li_li_li_a]:ml-[8px]"])

(def menu-item-class
  ["flex"
   "flex-column"
   "w-full"
   "items-center"
   "space-x-[8px]"
   "[padding:_6px_8px_6px_8px]"
   "rounded"
   "cursor-pointer"
   "hover:[text-decoration:none]"
   "[&.item-active]:bg-[theme(colors.surface-selected)]"
   "[&:not(.item-active)]:hover:text-inherit"
   "[&:not(.item-active)]:hover:bg-[theme(colors.surface-1)]"])

(def divider-class
  ["my-2"
   "mx-1.5"
   "text-[theme(colors.elements-assistive)]"
   "w-full"])

(def content-item-class
  ["flex"
   "flex-column"
   "select-none"
   "cursor-pointer"
   "[&_.chevron]:[transition:.15s_transform_ease]"
   "[&_details_::marker]:[display:none]"
   "[&_details[open]_.chevron_svg]:[transform:rotate(90deg)]"
   "[&_details:not[open]_.chevron_svg]:[transform:rotate(0deg)]"])

(def content-class
  ["p-2"
   "pr-[6px]"
   "mr-[5px]"
   "overflow-y-auto"
   "h-full"])

(defn on-open-menu
  [item]
  (hs-ui.utils/set-storage-item (:title item) true))

(defn on-close-menu
  [item]
  (hs-ui.utils/remove-storage-item (:title item)))

(defn open-before?
  [item]
  (hs-ui.utils/get-storage-item (:title item)))

(defn details-constructor
  [element item]
  (when element
    #?(:cljs
       (do
         (set! (.-ontoggle ^js/Object element)
               (fn [^js/Object event]
                 (if (= "open" (.-newState event))
                   (on-open-menu item)
                   (on-close-menu item))))
         (when (or (:open item) (open-before? item))
           (set! (.-open element) true))))))

(defn menu-item
  [item]
  [:a (utils/merge-props {:class (conj menu-item-class (when (:active item) "item-active"))} item)

   (when-let [img (:slot/img item)]
     [:figure {:class "min-w-[16px] w-[16px] min-h-[16px] h-[16px]"} img])


   (when-let [content (:slot/content item)]
     [:span {:class "truncate text-[theme(colors.elements-readable)]" :data-key :label}
      content])

   (when (:slot/items item)
     [:span.chevron hs-ui.svg.chevron-right/svg])])

(defn menu-items
  [node]
  [:ul {:class content-items-class :data-array :items}
   (for [item (:slot/items node)]
     [:li {:class content-item-class :key (or (:id item) (:title item) (hash item))}
      (cond
        (:slot/items item)
        [:details {:ref #(details-constructor % item)}
         [:summary [menu-item item]]
         [:div {:class "border-l pt-[2px] ml-[18.5px]"}
          [menu-items item]]]

        (:divider item)
        [:hr {:class divider-class}]

        :else [menu-item item])])])

(defn component
  "A sidebar with possibly nested entries.
  Has :slot/logo, :slot/header, :slot/subheader, :slot/menu, and :slot/submenu.
  :slot/menu and :slot/submenu consist of elements (items) with:
  - :slot/content
  - :id
  - :slot/items
  - :slot/img
  - :active (whether it's chosen)
  - :open
  - :divider (to add a dividing ruler)
  - :space (to add a ruler with some space around it)"
  [properties]
  [:aside (utils/merge-props {:class root-class :data-object ::component} properties)

   (when-let [header (:slot/header properties)]
     [:div {:class header-class} header])

   (when-let [subheader (:slot/subheader properties)]
     [:div {:class header-class} subheader])

   (when-let [menu (:slot/menu properties)]
     [:div {:class content-class :data-object :menu}
      [menu-items menu]])

   (when-let [submenu (:slot/submenu properties)]
     [:div {:class submenu-class}
      [menu-items submenu]])])
