(ns hs-ui.text
  (:require [hs-ui.utils :as utils]))

(defn label
  [props & children]
  (into
   [:label (utils/merge-props {:class "inline-block font-semibold leading-normal m-0"} props)]
   children))

(defn assistive
  [props & children]
  (into
   [:span (utils/merge-props {:class "text-assistive font-normal text-[theme(colors.color-elements-assistive)]"} props)]
   children))

(defn section-header
  [props & children]
  (into
   [:span (utils/merge-props {:class "font-heading text-section-header font-semibold text-[theme(colors.color-elements-readable)]"} props)]
   children))

(defn page-header
  [props & children]
  (into
   [:span (utils/merge-props {:class "font-heading text-page-header font-medium text-[theme(colors.color-elements-readable)]"} props)]
   children))
