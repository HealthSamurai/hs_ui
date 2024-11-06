(ns hs-ui.organisms.resource-editor
  (:require
   [hs-ui.text]
   [hs-ui.utils]
   [hs-ui.components.monaco]
   [hs-ui.components.button]
   [hs-ui.components.content-expand]
   [hs-ui.svg.check-2]
   [hs-ui.svg.chevron-double]))

(defn recalc-monaco-layout
  [editor]
  #?(:cljs (.layout editor (clj->js {}))))

(defn error-item
  [_ _]
  (let [open? (hs-ui.utils/ratom false)]
    (fn [monaco-editor error]
      [:<>
       [:tr {:class ["group/error-item hover:bg-[var(--color-surface-1)]" (when @open? "bg-[var(--color-surface-1)]")]}
        ;; TODO: add on click props for error (jump to code)
        [:td {:class "group-hover/error-item:underline cursor-pointer px-2 py-1 truncate text-[var(--color-critical-default)]"} (:type error)]
        [:td {:class "group-hover/error-item:underline cursor-pointer px-2 py-1 truncate text-[var(--color-elements-readable)]"} (:path error)]
        [:td.group.py-1.px-2
         [hs-ui.components.content-expand/component
          {:on-click #(do (swap! open? not)
                          (recalc-monaco-layout @monaco-editor))}]]]
       (when @open?
         [:tr {:colSpan 3}
          [:td
           [:pre {:class "pl-2 bg-white txt-code pb-2"}
            (hs-ui.utils/edn->json-pretty error)]]])])))

(defn valid-result
  [props]
  [:div {:class "py-2 px-4 flex space-x-2 bg-[var(--color-surface-1)]"}
   [:span {:class "text-[var(--color-elements-green)]"} hs-ui.svg.check-2/svg]
   [hs-ui.text/assistive (:valid-result-text props)]])

(defn result-loading
  [props]
  ;; TODO: add loading state
  )

(defn error-result
  [props monaco-editor]
  [:details {:class "group/item" :open true}
   [:summary {:class "py-2 px-4 flex items-center justify-between bg-[var(--color-critical-default)] cursor-pointer rounded-b-[var(--corner-corner-m)] group-open/item:rounded-b-none"
              :on-click (fn [] (recalc-monaco-layout @monaco-editor))}
    [:span {:class "flex items-center"}
     [hs-ui.text/assistive {:class "text-[var(--color-elements-readable-inv)]"} "Validation errors:"]
     [hs-ui.text/counter {:class "ml-2 rounded-[29px] px-1 pt-[1px] bg-[var(--color-elements-readable-inv)]"}
      (count (:errors props))]]

    [:span {:class "text-[var(--color-elements-readable-inv)] group-open/item:rotate-180"}
     hs-ui.svg.chevron-double/svg]

    [hs-ui.components.button/xs-red {:on-click (fn [e]
                                                 (hs-ui.utils/stop-propagation e)
                                                 )}
     "VALIDATE"]]
   [:div {:class "p-2 border border-t-0 border-[var(--color-critical-default)] rounded-b-[var(--corner-corner-m)]"}
    [:table.table-fixed.w-full
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
      [:div.flex.flex-col.h-full
       [monaco-editor-view monaco-editor monaco-props]
       [validation-result validation-props monaco-editor]])))
