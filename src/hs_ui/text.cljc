(ns hs-ui.text
  (:require [hs-ui.utils :as utils]))

(defn page-header
  [props & children]
  (into [:span (utils/merge-props {:class "txt-page-header"} props)] children))
(defn section-header [props & children]
  (into [:span (utils/merge-props {:class "txt-section-header"} props)] children))

(defn button-label-regular
  [props & children]
  (into [:span (utils/merge-props {:class "txt-button-label-regular"} props)] children))

(defn label
  [props & children]
  (into [:label (utils/merge-props {:class "inline-block txt-label"} props)] children))

(defn link
  [props & children]
  (into [:a (utils/merge-props {:class "txt-link"} props)] children))

(defn value
  [props & children]
  (into [:span (utils/merge-props {:class "txt-value"} props)] children))

(defn body
  [props & children]
  (into [:span (utils/merge-props {:class "txt-body"} props)] children))

(defn code
  [props & children]
  (into [:span (utils/merge-props {:class "txt-code"} props)] children))

(defn counter
  [props & children]
  (into [:span (utils/merge-props {:class "txt-counter"} props)] children))

(defn button-label-xs
  [props & children]
  (into [:span (utils/merge-props {:class "txt-button-label-xs"} props)] children))

(defn assistive
  [props & children]
  (into [:span (utils/merge-props {:class "inline-block txt-assistive"} props)] children))
