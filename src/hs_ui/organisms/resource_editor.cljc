(ns hs-ui.organisms.resource-editor
  (:require [hs-ui.components.monaco]
            [stylo.core :refer [c]]
            [clojure.string :as str]
            [stylo.tailwind.icons :as icons]
            [hs-ui.components.button]
            [suitkin.utils :as su]
            [re-frame.core :as rf]))

(def index ::index)

(defn humanize-error-type [error-type]
  (let [def (some-> error-type
                    (str/replace #"\-" " ")
                    str/capitalize)]
    (get {"unknown-key" "Unknown key"
          "fhir-schema-extensions-not-enabled" "FHIR Schema extensions not enabled"}
         error-type
         def)))

(defn error-item [{:keys [error on-error-click] :as params}]
  (let [*expanded? (su/ratom false)]
    (fn [{:keys [error on-error-click] :as params}]
      [:<>
       [:tr {:key (str (:key params) "-main")
             :on-click (fn [e]
                         #?(:cljs (.preventDefault e))
                         (swap! *expanded? not))
             :class [(c [:px "8px"] [:py "4px"] [:hover :cursor-pointer
                                                 [[".error-type"
                                                   {:text-decoration "underline"}]
                                                  [".error-path"
                                                   {:text-decoration "underline"}]
                                                  [".error-more"
                                                   {:display "flex"}]]])
                     (when @*expanded? (c [:bg :portal-error-bg-2]))]}
        [:td.error-type {:class (c [:text :portal-error] {:max-width "20vw"} [:px "8px"] {:border-radius "4px 0 0 4px"})
                         :on-click (fn [e]
                                     #?(:cljs (.stopPropagation ^js/Object e))
                                     (on-error-click error))}
         [:span {:class (c {:font-family "JetBrains Mono"} :truncate)}
          (humanize-error-type (:type error))]]
        [:td.error-path {:class (c {:max-width "20vw"} [:w "100%"] :truncate {:direction "rtl"})}
         [:span {:class (c {:font-family "JetBrains Mono"}) :on-click (fn [e]
                                                                        #?(:cljs (.stopPropagation ^js/Object e))
                                                                        (on-error-click error))}
          (:path error)]]

        [:td.error-info {:on-click #(do
                                      #?(:cljs (.stopPropagation ^js/Object %))
                                      (swap! *expanded? not))
                         :class (c {:min-width "100px"} [:pr "8px"] {:border-radius "0px 4px 4px 0px"
                                                                     :user-select "none"}
                                   :absolute
                                   [:right 0]
                                   :sticky)}
         (if @*expanded?
           [:div {:class (c :flex :items-center :justify-end)
                  :on-click #(do
                               #?(:cljs (.stopPropagation ^js/Object %))
                               (swap! *expanded? not))}
            [:div "Less"]
            [:div {:class (c [:ml "6px"])}
             icons/ic-extra-hide]]

           [:div.error-more {:class (c :flex :items-center :justify-end :hidden)}
            [:div "More"]
            [:div {:class (c [:ml "6px"])}
             icons/ic-extra-show]])]]
       (when @*expanded?
         [:tr {:key (str (:key params) "-main-expanded")}

          [:td {:colSpan 3}
           [:pre {:class (c [:pl "8px"] [:bg :white] :border-none
                            {:font-size "14px"
                             :text-wrap "wrap"
                             :font-family "JetBrains Mono"
                             :line-height "21px"
                             :letter-spacing "0.2px"})}
            (su/edn->json-pretty error)]]])])))


(rf/reg-sub
 ::is-validation-panel-expand?
 (fn [db [_ id]]
   (get-in db [index id :is-validation-panel-expand?])))

(defn validation-panel
  [{:keys [id data
           is-expanded?
           valid-summary
           on-expand-click on-validate on-error-click]}]
  (let [errors (get-in data [:data :error])
        error? (seq errors)]
    (if error?
      [:details.group {:id id
                       :open is-expanded?
                       :class (c [:mt "2px"])}
       [:summary.cursor-pointer
        {:on-click on-expand-click
         :class [(c :flex :justify-between
                    [:px "16px"] [:py "3px"]
                    :items-center
                    [:bg :portal-error-bg]
                    {:grid-template-columns "repeat(3, minmax(0, 1fr))"})
                 (when (not is-expanded?) (c :rounded-b))]}
        [:div {:class (c :items-center :flex [:text :white] {:font-size "12px"})}
         [:div {:class (c :text-center :items-center :flex)} "Errors"]
         [:span {:class (c [:px "4px"] [:ml "8px"]
                           :text-center
                           [:text :portal-primary-red]
                           [:bg :white]
                           {:border-radius "29px"
                            :line-height "18px"
                            :font-weight "700"
                            :display "block"})}
          (count errors)]]

        [:div {:class (c :flex {:place-content "center"})}
         [:div.chevron {:class "group-open:rotate-180"} icons/ic-chevron-double-up]]

        (if on-validate [hs-ui.components.button/xs-red {:on-click on-validate
                                                         :class "w-[72px]"} "VALIDATE"]
            [:div {:class "w-[72px]"}])]

       [:div {:class (c [:h "400px"]
                        [:bg :white]
                        {:border "1px var(--basic-red-5) solid"
                         :border-radius "0 0 6px 6px"})}
        [:div.scrollable-container {:class (c :overflow-y-auto
                                              [:h "100%"]
                                              [:mx "4px"]
                                              {:animation  "1s show-overflow"})}
         [:table {:class (c {:table-layout "auto"
                             :border-collapse "separate"
                             :border-spacing "0px 8px"})}
          [:tbody
           (->> errors
                (map-indexed (fn [index error]
                               [error-item {:key index
                                            :error error
                                            :on-error-click on-error-click}]))
                doall)]]]]]
      [:div {:class (c {:border-top "2px white solid" :background-color "#F9F9F9"}
                       {:border-radius "0 0 6px 6px"})}
       [:div {:class [(c :flex :justify-between :items-center  [:px "16px"] [:py "4px"])
                      (c {:border-radius "0 0 6px 6px"}
                         {:background-color "#F9F9F9"})]}

        [:div {:class [(c [:text :portal-assistive])]}
         valid-summary]]])))

(defn component [{monaco-props :c/monaco-props
                  validation-props :c/validation-props :as _props}]
  (let [id "resource-editor-validation-panel"
        validation-props (merge {:id id
                                 :data {:data {:error [{:type "Invalid JSON", :path ""}]}}
                                 :valid-summary "Resource is valid"
                                 :on-expand-click #(rf/dispatch [::set-toggle-errors-value id (not is-expanded?)])
                                 :is-expanded? @(rf/subscribe [::is-validation-panel-expand? id])}
                                validation-props)]
    [:div.flex.flex-col.h-fit
     [:div.h-full.overflow-auto
      [hs-ui.components.monaco/component monaco-props]]
     [validation-panel validation-props]]))

(comment
  (let [height (hs-ui.utils/ratom 200)])
  #?(:cljs
     (let [start-y (.-clientY e)]
       ;; Temporary function to handle resizing
       (letfn [(resize [move-e]
                 (prn "resize")
                 (prn (- (.-clientY move-e) start-y))
                 (let [new-height (+ 200 (- (.-clientY move-e) start-y))]
                   (reset! height new-height)))
               (stop-resize []
                 (prn "stop-resize")
                 (js/document.removeEventListener "mousemove" resize)
                 (js/document.removeEventListener "mouseup" stop-resize))]
         ;; Add mousemove and mouseup listeners
         (js/document.addEventListener "mousemove" resize)
         (js/document.addEventListener "mouseup" stop-resize))))
  )
