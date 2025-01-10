(ns hs-ui.components.dialog
  (:require
   [hs-ui.text]
   [hs-ui.components.button]
   [hs-ui.utils :as utils]))

(def show  utils/show-modal)
(def close utils/close-modal)

(defn component
  [& arguments]
  (let [properties (utils/get-component-properties arguments)
        children   (utils/get-component-children arguments)
        this       (utils/ratom nil)]
    [:dialog
     (utils/merge-properties
      {:class         "z-10 inset-0 rounded-lg p-0 backdrop-blur-md"
       :ref           #(when % (reset! this %))
       :on-mouse-down #(when (identical? (utils/get-event-target %) @this)
                         (close (:id properties)))}
      properties)

     [:div {:on-click #(utils/stop-propagation %)}
      children]]))

(defn template
  [props]
  (component
   props
   [:<>
    (when-let [header-text (:slot/header-text props)]
      [:div
       [hs-ui.text/page-header {:class "py-5 px-6 border-b border-[var(--color-border-default)]"} header-text]])
    (when-let [content (:slot/content props)]
      [:div {:class "py-5 px-6"}
       content])
    (when-let [action-menu (:slot/action-menu props)]
      [:div {:class "py-4 px-6 border-t border-[var(--color-border-default)] flex justify-end space-x-6"}
       action-menu])]))
