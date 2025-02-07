(ns hs-ui.elements.diff-table
  (:require
   [clojure.string :as str]
   [hs-ui.utils :as utils]
   [hs-ui.svg.diamond]
   [hs-ui.svg.choice]
   [hs-ui.svg.datatype]
   [hs-ui.svg.primitive]
   [hs-ui.svg.slice-item]
   [hs-ui.svg.external-link]))

(def table-class
  ["w-full"
   "h-[1px]"
   "font-[Inter]"
   "text-[12px]"
   "font-normal"])

(def th-class
  ["px-4"
   "py-2"
   "text-left"
   "bg-white"
   "text-[#616471]"])

(def td-class
  ["px-4"
   "py-2"
   "text-[#010205]"
   "font-inter"])

(defn nest-by-level
  [items]
  (loop [result []
         stack  []
         [x & xs] items]
    (if-not x
      result
      (let [node (assoc x :children [])
            lvl  (:lvl x)]

        (let [stack
              (loop [st stack]
                (if (empty? st)
                  st
                  (let [parent-lvl (:lvl (get-in result (peek st)))]
                    (if (>= parent-lvl lvl)
                      (recur (pop st))
                      st))))]

          (if (empty? stack)
            (let [idx     (count result)
                  result' (conj result node)]
              (recur result'
                     (conj stack [idx])
                     xs))

            (let [parent-path   (peek stack)
                  parent        (get-in result parent-path)
                  updated-parent (update parent :children conj node)
                  result'        (assoc-in result parent-path updated-parent)
                  new-child-idx  (dec (count (:children updated-parent)))
                  child-path     (conj parent-path :children new-child-idx)]

              (recur result'
                     (conj stack child-path)
                     xs))))))))

(defn name-cell [element]
  [:div {:class "flex pt-2 ml-1"}
   [:div {:class "pt-[2px]"}
    (cond
      (or (:extension-url element)
          (:slice-type element))
      [:span {:class "text-[--color-elements-green]"} hs-ui.svg.slice-item/svg]
      (= "primitive" (:type element))
      hs-ui.svg.primitive/svg
      (:union? element)
      [:span {:class "text-[--color-cta]"} hs-ui.svg.choice/svg]
      (= "Reference" (get element :datatype))
      [:span {:class  "text-[--color-cta]"} hs-ui.svg.external-link/svg]
      :else hs-ui.svg.datatype/svg)]
   [:div {:class "pl-2"}
    (:name element)
    (when (:union? element) "[x]")]])

(defn flags-cell [element]
  [:div {:class "flex flex-row h-full"}
   (when (contains? (:flags element) "mustSupport")
     [:span {:class "px-[2px] max-h-[20px] mr-1 text-white bg-[--color-critical-default] rounded"}
      "S"])
   (when (contains? (:flags element) "summary")
     [:span "Σ"])
   (when (contains? (:flags element) "modifier")
     [:span "!?"])])

(defn cardinality-cell [element]
  [:div {:class "flex flex-row h-full"}
   (when-not (= 0 (:lvl element))
     (str (or (:min element) 0) ".." (or (:max element) 1)))])

(defn datatype-cell [element]
  [:div {:class "flex flex-row h-full"}
   (if (:coordinate element)
     (let [[_ _ package-name package-version _ schema-name schema-version]
           (str/split (:coordinate element) #"/")]
       [:div
        [:a {:href (str "#/ig/" package-name "#" package-version "/sd/" schema-name "_" schema-version)
             :class "text-[#358FEA]"}
         schema-name]
        (:slice-type element)
        (when (:refers element)
          [:span {:class "space-x-2"}
           " ("
           (for [r (:refers element)] ^{:key (:name r)}
             [:a {:href (str "#/ig/introspector/"
                             (hs-ui.utils/encode-uri (:package r))
                             "?url="
                             (hs-ui.utils/encode-uri (:url r)))
                  :class "text-[#358FEA]"}
              (:name r)])
           ")"])])
     (:datatype element))])

(defn description-cell [element]
  [:<>
   (when-let [v (:short element)]
     [:div v])
   (when-let [v (:extension-url element)]
     [:div "URL: "
      [:a {:href (let [[_ _ package-name package-version _ extension-name extension-version]
                       (str/split (:extension-coordinate element) #"/")]
                   (str "#/ig/" package-name "#" package-version "/sd/" extension-name "_" extension-version))
           :class "text-[#358FEA]"} v]])
   (when-let [v (:binding element)]
     [:div "Binding: "
      [:a {:href (let [[_ _ package-name package-version _ value-set-name value-set-version]
                       (str/split (:vs-coordinate element) #"/")]
                   (str "#/ig/" package-name "#" package-version "/vs/" value-set-name "_" value-set-version))
           :class "text-[#358FEA]"}
       (get v "valueSet") " (" (get v "strength") ")"]])])

(defn tree-node [{:keys [name children flags min short union datatype binding lvl path] :as element} last-childs]
  (if (empty? children)
    (let [last-child? (->> last-childs
                           (filter #(= path (:path %)))
                           (not-empty))]
      [:tr {:class "group"}
       [:td {:class (utils/class-names td-class "flex h-full pl-[15px] py-0")}
        [:div {:class "element flex h-full"}
         (for [i (range lvl)]
           ^{:key i}
           [:span {:class "block li w-[15px] h-auto"}] )]
        [:div {:class "z-10 bg-white group-even:bg-[#f7f7f8]"}
         [name-cell element]
         (when last-child?
           [:div {:class "relative -left-[15px] h-full -top-[10px] border-l border-white group-even:border-[#f7f7f8]"}])]]
       [:td {:class (utils/class-names td-class "space-x-2")}
        [flags-cell element]]
       [:td {:class td-class
             :style {:font-family "JetBrains Mono"}}
        [cardinality-cell element]]
       [:td {:class td-class}
        [datatype-cell element]]
       [:td {:class (utils/class-names td-class "font-[12px]")}
        [description-cell element]]])

    [:<>
     [:tr {:class "group"}
      [:td {:class (utils/class-names td-class "flex h-full pl-[15px] py-0")}
       [:div {:class "element flex h-full"}
        (for [_ (range lvl)]
          [:span {:class "block li w-[15px] h-auto"}])]
       [:div {:class "z-50 bg-white group-even:bg-[#f7f7f8]"}
        [name-cell element]
        (when (not= 0 lvl)
          [:div {:class "ml-[10px] h-[calc(100%-6px)] border-l border-dotted border-[#b3bac0]"}])]]
      [:td {:class (utils/class-names td-class "space-x-2")}
       [flags-cell element]]
      [:td {:class td-class
            :style {:font-family "JetBrains Mono"}}
       [cardinality-cell element]]
      [:td {:class td-class}
        [datatype-cell element]]
      [:td {:class (utils/class-names td-class "font-[12px]")}
       [description-cell element]]]

     (for [child children]
       ^{:key (:name child)}
       [tree-node child last-childs])]))

(defn view [elements & [options]]
  (let [nested-elements (nest-by-level elements)
        last-childs (->> elements
                         (map-indexed vector)
                         (filter (fn [[idx {lvl :lvl}]]
                                   (let [next-el-lvl (-> (nth elements (inc idx) 0)
                                                         :lvl)]
                                (or (= (dec lvl) next-el-lvl) (= 0 next-el-lvl)))))
                         (map second))]

    [:div {:class "pl-2"}
     [:table {:class table-class}
      [:style "table tr:nth-child(even) {background-color: #f7f7f8;}

   tbody.tree, tbody.tree tbody {
    list-style: none;
     margin: 0;
     padding: 0;
   }
   tbody.tree .li {
     margin: 0;
    margin-left: 10px;
     padding: 0 7px;
     line-height: 20px;
    font-family: Inter;
     border-left:1px dotted #b3bac0;
   }
   tbody.tree li:last-child {
       border-left: none;
   }

   tbody.tree > tr > td > .element > span:last-child {
       color: red;
   }

   tbody.tree > tr > td > .element > span:last-child:before {
      position:relative;
      top:0.3em;
      height:1em;
      width:12px;
      color:white;
      border-bottom:1px dotted #b3bac0;
      content: '';
      display:inline-block;
      left:-7px;
   } "]

      [:thead
       [:tr {:class "sticky top-0 z-50"}
        [:th {:class th-class} "Name"]
        [:th {:class th-class} "Flags"]
        [:th {:class th-class} "Card."]
        [:th {:class th-class} "Type"]
        [:th {:class th-class} "Description"]]]
      [:tbody.tree
       (for [node nested-elements]
         ^{:key (:name node)}
         [tree-node node last-childs])]]]))
