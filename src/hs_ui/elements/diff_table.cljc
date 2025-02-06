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

(def left-class
  {:negative "after:left-[-13px]"
   0 "after:left-[0px]"
   1 "after:left-[7px]"
   2 "after:left-[21px]"
   3 "after:left-[28px]"
   4 "after:left-[35px]"
   5 "after:left-[42px]"
   6 "after:left-[49px]"
   7 "after:left-[56px]"
   8 "after:left-[63px]"
   9 "after:left-[70px]"
   10 "after:left-[77px]"})

(defn view [elements & [options]]
  [:div {:class "pl-2"}
   [:style ".fhir-structure tr:nth-child(even) {background-color: #f7f7f8;}"]
   [:table.fhir-structure {:class table-class}
    [:thead
     [:tr {:class "sticky top-0 z-10"}
      [:th {:class th-class} "Name"]
      [:th {:class th-class} "Flags"]
      [:th {:class th-class} "Card."]
      [:th {:class th-class} "Type"]
      [:th {:class th-class} "Description"]]]
    [:tbody
     (doall
      (map-indexed
       (fn [idx element]
         ^{:key (or (:path element) (:type element))}
         (let [lvl (:lvl element)
               next-el-lvl (-> (nth elements (inc idx) 0)
                               :lvl)
               prev-el-lvl (-> (nth elements (dec idx) 0)
                               :lvl)
               has-children? (= (inc lvl) next-el-lvl)
               first-child? (= (dec lvl) prev-el-lvl)
               last-child? (or (= (dec lvl) next-el-lvl) (= 0 next-el-lvl))]
           [:tr {:class "group"
                 :style {:height "max-content"}}
            [:td {:style {:margin-left (if (= 0 lvl) "0px" "25px")
                          :margin-top "4px"
                          :height "100%"}
                  :class (utils/class-names "flex items-center relative align-top"
                                            (when-not (= 0 lvl)
                                              "relative after:content-[''] after:absolute after:top-0 after:left-0 after:h-full after:border-l after:border-dotted after:border-[#b3bac0]"))}
             (if
               (= 0 lvl)
               [:div {:class "min-w-[18px]"}]
               [:div {:style {:margin-left (if (> 1 lvl) "0px" (str (* (- lvl 1) (if (> lvl 2) 23 27)) "px"))}
                      :class "absolute top-[10px] border-b border-dotted border-[#b3bac0] min-w-[15px]"}])
             [:div {:class "flex flex-row h-full"}
              [:div {:style {:margin-left (if (> 1 lvl) "0px" (str (* lvl 20) "px"))}
                     :class (cond
                              (or (and (not= 0 lvl) (< lvl next-el-lvl))
                                  (and (< 1 lvl) (= (inc prev-el-lvl) lvl))
                                  (and (< 1 lvl) (= prev-el-lvl next-el-lvl)))

                              (utils/class-names  "relative after:content-[''] after:absolute after:top-[20px] after:h-full after:border-l after:border-dotted after:border-[#b3bac0]"
                                                  (get left-class (cond

                                                                    has-children?
                                                                    1

                                                                    (or (and (= prev-el-lvl next-el-lvl)
                                                                             (< 1 lvl))
                                                                        (and first-child? (< 1 lvl)))
                                                                    :negative

                                                                    :else lvl)))

                              last-child?
                              (utils/class-names  "relative after:content-[''] after:absolute after:top-[10px] after:h-1/2 after:border-l after:border-white group-even:after:border-[#f7f7f8]"
                                                  (get left-class :negative)))}


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
              [:span {:class "pl-2"}
               (:name element)
               (when (:union? element) "[x]")]]]
            [:td {:class "space-x-2 px-4"}
             [:div {:class "flex flex-row h-full"}
              (when (contains? (:flags element) "mustSupport")
                [:span {:class "px-[2px] text-white bg-[--color-critical-default] rounded"}
                 "S"])
              (when (contains? (:flags element) "summary")
                [:span "Σ"])
              (when (contains? (:flags element) "modifier")
                [:span "!?"])]]
            [:td {:class (utils/class-names td-class "px-4 pt-0" )
                  :style {:font-family "JetBrains Mono"}}
             [:div {:class "flex flex-row h-full"}
              (when-not (= 0 (:lvl element))
                (str (or (:min element) 0) ".." (or (:max element) 1)))]]
            [:td {:class (utils/class-names td-class "px-4 pt-0")}
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
                (:datatype element))]]

            [:td {:class (utils/class-names td-class "px-4 font-[12px]")}
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
                 (get v "valueSet") " (" (get v "strength") ")"]])]]))
       elements))]]])
