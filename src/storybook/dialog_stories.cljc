(ns storybook.dialog-stories
  (:require
   [hs-ui.core]
   [hs-ui.components.dialog]
   [storybook.utils :as utils]))


(def ^:export default
  (clj->js
   {:title     "Molecules/Dialog"
    :component (utils/reagent-reactify-component hs-ui.core/dialog)}))

(def ^:export Basic
  (clj->js {:args {:severity "error"}
            :render (fn [args]
                      (utils/reagent-as-element
                       [:div [hs-ui.core/button-primary {:on-click #(hs-ui.components.dialog/show "my-modal")}
                             "Open modal"]
                        [hs-ui.core/dialog {:id "my-modal"}
                         [hs-ui.core/layout-confirmation
                          {:slot/right [[hs-ui.core/button-tertiary
                                         {:on-click #(hs-ui.components.dialog/close "my-modal")}
                                         "Close"]]}]]]))}))
