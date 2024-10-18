(ns hs-ui.components.dialog
  (:require
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

     [:div {:on-click #(utils/stop-propagation %)} children]]))
