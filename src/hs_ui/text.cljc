(ns hs-ui.text
  (:require [hs-ui.utils :as utils]))

(defn page-header
  [props & children]
  (into [:span (utils/merge-props {:class "txt-page-header text-[theme(colors.elements-readable)]"} props)] children))

(defn section-header [props & children]
  (into [:span (utils/merge-props {:class "txt-section-header text-[theme(colors.elements-readable)]"} props)] children))

(defn button-label-regular
  [props & children]
  (into [:span (utils/merge-props {:class "txt-button-label-regular text-[theme(colors.elements-readable)]"} props)] children))

(defn label
  [& attrs]
  (let [props (utils/get-component-properties attrs)
        child (utils/get-component-children attrs)]
    [:label
     (utils/merge-props
      {:class "inline-block txt-label text-[theme(colors.elements-readable)]"}
      props)
     child]))

(defn link
  [props & children]
  (into [:a (utils/merge-props {:class "txt-link text-[theme(colors.cta)]"} props)] children))

(defn value
  [props & children]
  (into [:span (utils/merge-props {:class "txt-value text-[theme(colors.elements-readable)]"} props)] children))

(defn body
  [props & children]
  (into [:span (utils/merge-props {:class "txt-body text-[theme(colors.elements-readable)]"} props)] children))

(defn code
  [props & children]
  (into [:span (utils/merge-props {:class "txt-code text-[theme(colors.elements-readable)]"} props)] children))

(defn counter
  [& attrs]
  (let [props (utils/get-component-properties attrs)
        child (utils/get-component-children attrs)]
    [:span
     (utils/merge-props
      {:class "txt-counter text-[theme(colors.critical-default)]"}
      props)
     child]))

(defn button-label-xs
  [props & children]
  (into [:span (utils/merge-props {:class "txt-button-label-xs text-[theme(colors.cta)]"} props)] children))

(defn assistive
  [& attrs]
  (let [props (utils/get-component-properties attrs)
        child (utils/get-component-children attrs)]
    [:span
     (utils/merge-props
      {:class "inline-block txt-assistive text-[theme(colors.elements-assistive)]"}
      props)
     child]))

(defn home
  [props & children]
  (into [:span (utils/merge-props {:class "inline-block txt-home text-[theme(colors.elements-readable)]"} props)] children))
