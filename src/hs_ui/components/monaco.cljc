(ns hs-ui.components.monaco
  (:require
   [hs-ui.utils]
   #?(:cljs ["@monaco-editor/react" :default Editor])))


(defn set-json-defaults
  [monaco-instance urls & [default-path]]
  #?(:cljs
     (when urls
       (.setDiagnosticsOptions (.-jsonDefaults (.-json (.-languages ^js/Object monaco-instance)))
                               (clj->js {:validate true
                                         :allowComments true
                                         :enableSchemaRequest true
                                         :schemas (mapv (fn [url] {:uri url :fileMatch [(or default-path "*")]})
                                                        urls)})))
     :clj nil))

(defn component
  [properties]
  (fn [properties]
    [:> #?(:cljs Editor
           :clj [:input {:id (:id properties)
                         :value (:value properties)
                         :on-change (fn [event]
                                      ((:onChange properties)
                                       (hs-ui.utils/target-value event)))}])
     (->
      {:key (:id properties)
       :theme    (:theme properties "hs-ui-theme")
       :defaultPath (:defaultPath properties)
       :defaultValue (:defaultValue properties)
       :language "json"
       :automaticLayout true
       :options  (merge {:minimap                          {:enabled false}
                         "bracketPairColorization.enabled" false
                         :fontSize                         "14px"
                         :automaticLayout                  true
                         :fontStyle                        "normal"
                         :scrollbar                        {:verticalScrollbarSize   6
                                                            :horizontalScrollbarSize 6}
                         :padding                          {:top 16}
                         :lineHeight                       "1.5"
                         :letterSpacing                    "0.2em"
                         :fontWeight                       "300"
                         :fontFamily                       "JetBrains Mono"
                         :overviewRulerLanes               0
                         :glyphMargin                      true
                         :renderLineHighlight              "none"
                         :folding                          true
                         :renderIndentGuides               true
                         :lineDecorationsWidth             0
                         :tabSize                          2
                         ;; :lineNumbers                      "off"
                         :lineNumbersMinChars              3
                         :scrollBeyondLastLine             false
                         }
                        (:options properties))
       :onMount
       (fn [editor instance]
         (when-let [on-mount-fn (:on-mount-fn properties)]
           (on-mount-fn editor instance)))
       :beforeMount
       (fn [instance]
         (when-let [before-mount-fn (:before-mount-fn properties)]
           (before-mount-fn instance))
         (when (:schemas properties)
           (set-json-defaults instance (:schemas properties) (:defaultPath properties)))
         (.setMonarchTokensProvider (.-languages ^js/Object instance) "yaml"
                                    (clj->js
                                     {:tokenPostfix nil

                                      :escapes #"\\(?:[abefnrtv0_NLP\\\"]|(?:x[\dA-Fa-f]{2})|(?:u[\dA-Fa-f]{4})|(?:U[\dA-Fa-f]{8}))"

                                      :tokenizer
                                      {:root [[#"(^\s*)([a-z_$][\w$-]*)(:(?: |$))" ["white" "property-name" "operator"]]
                                              [#"#.*?$" "comment"]
                                              [#"[ \t\r\n]+" "white"]
                                              [#"'" {:token "string.quote" :bracket "@open" :next "litstring"}]
                                              [#"\"([^\"\\]|\\.)*$" "string.invalid"]
                                              [#"\"" {:token "string.quote" :bracket "@open" :next "string"}]]

                                       :string [[#"[^\\\"]+" "string"]
                                                ["@escapes" "string.escape"]
                                                [#"\\." "string.escape.invalid"]
                                                [#"\"" {:token "string.quote" :bracket "@close" :next "@pop"}]]

                                       :litstring [[#"[^']+" "string"]
                                                   [#"''" "string.escape"]
                                                   [#"'" {:token "string.quote" :bracket "@close" :next "@pop"}]]}}))
         #?(:cljs
            (.defineTheme (.-editor ^js/Object instance)
                          "hs-ui-theme"
                          (clj->js {:base    "vs"
                                    :inherit true
                                    :rules   [{:token "string.key.json" :foreground "#EA4A35"}
                                              {:token "property-name" :foreground "#EA4A35"}
                                              {:token "litstring" :foreground "#EA4A35"}
                                              {:token "white" :foreground "#EA4A35"}
                                              {:token "string.value.json" :foreground "#405CBF"}
                                              {:token "string" :foreground "#405CBF"}
                                              {:token "number" :foreground "#00A984"}
                                              {:token "key.json" :foreground "#00A984"}]
                                    :colors  {"editor.background" "#FFFFFF"
                                              "editor.foreground" "#405CBF"
                                              "editorLineNumber.foreground" "#83868E"
                                              "editorLineNumber.activeForeground" "#000000"
                                              "scrollbar.shadow"  "#ffffff00"
                                              "scrollbarSlider.background" "#83868E"
                                              "scrollbarSlider.activeBackground" "#212636"
                                              "scrollbarSlider.hoverBackground" "#212636"
                                              "widget.shadow"     "#ffffff00"}}))
            :clj nil)
         instance)}
      (merge (dissoc properties :options)))]))
