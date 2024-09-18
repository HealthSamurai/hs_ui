(ns storybook.corners-stories
  (:require [storybook.utils :as utils]))

(def ^:export default
  (clj->js {:title "Atoms/Corners"}))

(defn box
  [class]
  [:div.p-x2.border.border-red-500 {:class class}])

(defn ^:export XS
  [props]
  (utils/reagent-as-element [box "rounded-corner-xs"]))

(defn ^:export S
  [props]
  (utils/reagent-as-element [box "rounded-corner-s"]))

(defn ^:export M
  [props]
  (utils/reagent-as-element [box "rounded-corner-m"]))

(defn ^:export L
  [props]
  (utils/reagent-as-element [box "rounded-corner-l"]))

(defn ^:export MAX
  [props]
  (utils/reagent-as-element [box "rounded-corner-max"]))
