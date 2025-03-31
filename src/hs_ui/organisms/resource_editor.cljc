(ns hs-ui.organisms.resource-editor
  (:require
   [hs-ui.text]
   [hs-ui.utils]
   [hs-ui.components.monaco]
   [hs-ui.components.button]
   [hs-ui.components.content-expand]
   [hs-ui.svg.loading]
   [hs-ui.svg.check-2]
   [hs-ui.svg.chevron-double]
   [re-frame.core :as rf]
   [clojure.string :as str]
   #?(:cljs ["@mischnic/json-sourcemap" :as jsonMap])
   #?(:cljs [goog.object])
   ))

(defn validator-path->json-map-path
  [path]
  (if (and (string? path) (seq path))
    (str "/" (str/replace (str/replace path #"\.|\[|\]\." "/") #"\]" ""))
    ""))

(defn path->line
  "Get the line and column error resides on in the editor.
  Return a [LINE-NUMBER COLUMN-NUMBER] vector."
  [monaco-editor path]
  #?(:cljs
     (let [json-string (.getValue ^js/Object monaco-editor)
           json-map (jsonMap/parse json-string nil (clj->js {:dialect "JSON5"}))
           json-map-pointers (.-pointers json-map)
           position (or (goog.object/get json-map-pointers (validator-path->json-map-path path))
                        (clj->js {:key {:line 0 :column 0}}))
           line-number (inc (.-line (or (.-key position)
                                        (.-value position))))
           column-number (inc ^js/Object (.-column (or (.-key position)
                                                       (.-value position))))]
       [line-number column-number])))

(rf/reg-fx
 ::monaco-goto-line
 (fn [{:keys [monaco-editor path] :as _options}]
   #?(:cljs
      (let [json-string (.getValue ^js/Object monaco-editor)]
        (when (seq json-string)
          (let [[line-number column-number]          (path->line monaco-editor path)
                decoration (clj->js [{:range         {:startLineNumber line-number
                                                      :endLineNumber   line-number
                                                      :startColumn     1
                                                      :endColumn       1}
                                      :options       {:isWholeLine true
                                                      :className "fade-out bg-red-300"}}])]


            ;; Reveal and position cursor at the line
            (.revealLineNearTop ^js/Object monaco-editor line-number)
            (.setPosition ^js/Object monaco-editor (clj->js {:column     column-number
                                                             :lineNumber line-number}))
            (.focus ^js/Object monaco-editor)

            ;; Apply decoration and remove after 1 second
            (let [decoration-ids (.deltaDecorations ^js/Object monaco-editor #js [] decoration)]
              (js/setTimeout
               (fn []
                 (.deltaDecorations ^js/Object monaco-editor decoration-ids #js []))
               1000))))))))

(rf/reg-event-fx
 ::monaco-goto-line
 (fn [_ [_ options]]
   {::monaco-goto-line options}))

(defn recalc-monaco-layout
  [editor]
  #?(:cljs (.layout editor (clj->js {}))))

(def open-errors (hs-ui.utils/ratom {}))

(defn close-errors []
  (run! (fn [[error closing-function]]
          (closing-function error))
        @open-errors)
  (reset! open-errors {}))

(defn error-item
  [_ _]
  (let [open? (hs-ui.utils/ratom false)]
    (fn [monaco-editor error]
      [:<>
       [:tr {:class ["w-fit group/error-item hover:bg-[var(--color-surface-1)]" (when @open? "bg-[var(--color-surface-1)]")]
             :on-click (fn []
                         (swap! open? not)
                         (close-errors)
                         (when @open?
                           (reset! open-errors {error #(reset! open? false)}))
                         (recalc-monaco-layout @monaco-editor))}
        [:td {:class "max-w-[20vw] group-hover/error-item:underline cursor-pointer px-2 py-1 truncate text-[var(--color-critical-default)]"
              :on-click #(rf/dispatch [::monaco-goto-line {:path (:path error)
                                                           :monaco-editor @monaco-editor}])}
         (:type error)]
        [:td {:class "max-w-[20vw] w-full group-hover/error-item:underline cursor-pointer px-2 py-1 truncate text-[var(--color-elements-readable)] text-right"
              :on-click #(rf/dispatch [[::monaco-goto-line {:path (:path error)
                                                            :monaco-editor @monaco-editor}]])}
         (:path error)]]
       (when @open?
         [:tr
          [:td {:colSpan 3 :class "overflow-x-auto max-w-[100px]"}
           [:pre {:class "pl-2 bg-white txt-code pb-2 text-wrap"}
            (hs-ui.utils/edn->json-pretty error)]]])])))

(defn valid-result
  [props]
  [:div {:class "py-2 px-4 flex space-x-2 bg-[var(--color-surface-1)] rounded-b-[var(--corner-corner-m)]"}
   [:span {:class "text-[var(--color-elements-green)]"} hs-ui.svg.check-2/svg]
   [hs-ui.text/assistive (:valid-result-text props)]])

(defn result-loading
  [props]
  (let [has-errors? (not-empty (:errors props))]
    [:div {:class ["py-2 px-4 flex space-x-2 rounded-b-[var(--corner-corner-m)] items-center"
                   (if has-errors?
                     "bg-[var(--color-critical-default)]"
                     "bg-[var(--color-surface-1)]")]}
     [:span.animate-spin
      {:style {:filter (if has-errors?
                         "brightness(0) saturate(100%) invert(100%) sepia(100%) saturate(0%) hue-rotate(340deg) brightness(100%) contrast(101%)"
                         "invert(47%) sepia(7%) saturate(673%) hue-rotate(183deg) brightness(96%) contrast(87%)")}}
      hs-ui.svg.loading/svg-16]
     [hs-ui.text/assistive {:class (when has-errors? "text-[var(--color-elements-readable-inv)]")}
      "validating..."]]))

(defn error-result
  [props monaco-editor]
  [:details {:class "group/item"}
   [:summary {:class "h-[32px] py-2 px-4 flex items-center justify-between bg-[var(--color-critical-default)] cursor-pointer rounded-b-[var(--corner-corner-m)] group-open/item:rounded-b-none"
              :on-click (fn [] (recalc-monaco-layout @monaco-editor))}
    [:span {:class "flex items-center"}
     [hs-ui.text/assistive {:class "text-[var(--color-elements-readable-inv)]"} "Validation errors:"]
     [hs-ui.text/counter {:class "ml-2 rounded-[29px] px-1 pt-[1px] bg-[var(--color-elements-readable-inv)]"}
      (count (:errors props))]]

    [:span {:class "text-[var(--color-elements-readable-inv)] group-open/item:rotate-180"}
     hs-ui.svg.chevron-double/svg]

    [hs-ui.components.button/xs-red {:class "px-2"
                                     :on-click (fn [e]
                                                 (hs-ui.utils/stop-propagation e)
                                                 (when-let [validate-fn (:validate-fn props)]
                                                   (validate-fn)))}
     "VALIDATE"]]
   [:div {:class "p-2 border border-t-0 border-[var(--color-critical-default)] rounded-b-[var(--corner-corner-m)]"}
    [:table.table-auto.w-full
     [:tbody
      (for [error (:errors props)] ^{:key (hash error)}
        [error-item monaco-editor error])]]]])

(defn validation-result
  [props monaco-editor]
  (cond
    (:loading? props)
    [result-loading props]

    (empty? (:errors props))
    [valid-result props]

    (seq (:errors props))
    [error-result props monaco-editor]))

(defn monaco-editor-view
  [monaco-editor monaco-props]
  [:div.h-full.mb-1 {:class "bg-[var(--color-surface-1)] rounded-t-[var(--corner-corner-m)] p-1"}
   [hs-ui.components.monaco/component
    (assoc monaco-props :on-mount-fn
           (fn [editor instance]
             (when-let [on-mount-fn (:on-mount-fn monaco-props)]
               (on-mount-fn editor instance))
             (reset! monaco-editor editor)))]])

(defn component [_]
  (let [monaco-editor (hs-ui.utils/ratom nil)] ;; Needs for recalculate monaco layout on expand
    (fn [{monaco-props :c/monaco-props validation-props :c/validation-result}]
      ;; TODO: Use horizontal split view?
      [:div.flex.flex-col.h-full
       [monaco-editor-view monaco-editor monaco-props]
       [validation-result validation-props monaco-editor]])))
