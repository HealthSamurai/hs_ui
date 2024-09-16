(ns hs-ui.graphic
  (:require [hs-ui.utils :as utils]))

(defn separator
  [props]
  [:hr (utils/merge-props {:class "flex-1 border-[theme(colors.color-separator)]"} props)])
