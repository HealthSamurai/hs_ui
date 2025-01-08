(ns hs-ui.components.sidebar
  (:require
   [hs-ui.utils :as utils]
   [hs-ui.svg.chevron-right]))

(def root-class
  ["w-[240px]"
   "h-[100vh]"
   "flex"
   "text-[--basic-gray-7]"
   "bg-white"
   "font-normal"
   "[border-right:1px_solid_var(--basic-gray-2)]"
   "[flex-flow:column]"
   "[line-height:20px]"
   "[font-size:14px]"])

(def submenu-class
  ["p-1"
   "pb-0"
   "mb-4"
   "[border-top:1px_solid_var(--basic-gray-2)]"])

(def header-class
  ["flex"
   "items-center"
   "space-x-1"
   "py-1"
   "px-4"])

(def content-items-class
  ["w-full"
   "[&_details]:w-full"
   "[&.root>li:last-child]:mb-[100px]"
   "[&_a]:hover:text-inherit"
   "[&_li_a]:pl-[16px]"
   "[&_li_li_a]:pl-[40px]"
   "[&_li_li_li_a]:pl-[64px]"])

(def menu-item-class
  ["flex"
   "flex-column"
   "w-full"
   "items-center"
   "space-x-2"
   "[padding:_6px_16px_6px_0px]"
   "rounded"
   "cursor-pointer"
   "hover:[text-decoration:none]"
   "[&.item-active]:bg-[var(--basic-gray-1)]"
   "[&:not(.item-active)]:hover:text-inherit"
   "[&:not(.item-active)]:hover:bg-[var(--basic-gray-0)]"])

(def divider-class
  ["my-2"
   "mx-1.5"
   "text-[var(--basic-gray-2)]"
   "w-full"])

(def content-item-class
  ["flex"
   "flex-column"
   "select-none"
   "cursor-pointer"
   "[&_.chevron]:[transition:.15s_transform_ease]"
   "[&_details[open]_.chevron_svg]:[transform:rotate(90deg)]"
   "[&_details:not[open]_.chevron_svg]:[transform:rotate(0deg)]"])

(def content-class
  ["ml-1"
   "mr-1"
   "mt-1"
   "overflow-y-auto"
   "h-full"])

(defn open-menu-item?
  [item]
  (some :active (:slot/items item)))

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
  [props]
  (let [open? (hs-ui.utils/ratom false)]
    (fn [item]
      [:a (utils/merge-props
           {:class (vec
                    (concat
                     menu-item-class
                     [(when (:active item) "item-active")
                      (when-not (:title item)
                        "flex justify-center")]))}
           props)
       (:slot/img item)
       (when (:active item)
         [:data {:hidden true :data-key :active} (:active item)])
       (when (:title item)
         [:span {:data-key :label :class "w-full truncate text-[--basic-gray-7]"}
          (:title item)])
       (when (:slot/items item)
         [:span.chevron hs-ui.svg.chevron-right/svg])])))

(defn menu-items
  [node]
  [:ul {:class content-items-class :data-array :items}
   (for [item (:slot/items node)]
     [:li {:class content-item-class :key (or (:id item) (:title item) (hash item))}
      (cond
        (:slot/items item)
        [:details {:ref #(details-constructor % item)}
         [:summary
          (when (or (:open item) (open-before? item))
            [:data {:hidden true :data-key :open} (:open item)])
          [menu-item item]]
         [:div.content [menu-items item]]]
        (:divider item)
        [:hr {:class divider-class}]
        (:space item)
        [:hr {:class "pb-4px"}]
        :else [menu-item item])])])

(defn component
  "A sidebar with possibly nested entries.
  Has :slot/logo, :slot/header, :slot/subheader, :slot/menu, and :slot/submenu.
  :slot/menu and :slot/submenu consist of elements (items) with:
  - :title
  - :id
  - :slot/items
  - :slot/img
  - :active (whether it's chosen)
  - :open
  - :divider (to add a dividing ruler)
  - :space (to add a ruler with some space around it)"
  [properties]
  [:aside (utils/merge-props {:data-object ::component :class root-class}
                             properties)
   [:div {:class header-class}
    (:slot/logo properties)
    (:slot/header properties)]

   (when (:slot/subheader properties)
     [:div {:class header-class}
      (:slot/subheader properties)])
   [:div {:data-object :menu :class content-class}
    [menu-items (:slot/menu properties)]]
   (when (:slot/submenu properties)
     [:div {:class submenu-class :data-object :submenu}
      [menu-items (:slot/submenu properties)]])])
