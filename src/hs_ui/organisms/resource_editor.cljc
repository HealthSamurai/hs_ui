(ns hs-ui.organisms.resource-editor
  (:require [hs-ui.components.monaco]))

(defn component
  [props]
  [:<>
   [:div.flex.flex-col.h-full
    [:div.h-full.overflow-auto.resize-y
     [hs-ui.components.monaco/component (:c/monaco-props props)]]
    [:div.resizer "Errors"]]])

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
