(ns storybook.spacings-stories
  (:require [storybook.utils :as utils]))

(def ^:export default
  (clj->js {:title "Atoms/Spacings"}))

(defn box
  [class]
  [:div.w-min.bg-red-500 {:class class}
   [:span.bg-white.text-nowrap "." class]])

(defn ^:export quarter
  [props]
  (utils/reagent-as-element [box "p-quarter"]))

(defn ^:export half
  [props]
  (utils/reagent-as-element [box "p-half"]))

(defn ^:export x1
  [props]
  (utils/reagent-as-element [box "p-x1"]))

(defn ^:export x1point5
  [props]
  (utils/reagent-as-element [box "p-x1point5"]))

(defn ^:export x2
  [props]
  (utils/reagent-as-element [box "p-x2"]))

(defn ^:export x3
  [props]
  (utils/reagent-as-element [box "p-x3"]))

(defn ^:export x4
  [props]
  (utils/reagent-as-element [box "p-x4"]))

(defn ^:export x6
  [props]
  (utils/reagent-as-element [box "p-x6"]))

(defn ^:export x8
  [props]
  (utils/reagent-as-element [box "p-x8"]))

(defn ^:export x12
  [props]
  (utils/reagent-as-element [box "p-x12"]))
