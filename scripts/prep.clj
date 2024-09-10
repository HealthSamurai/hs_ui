(ns prep
  (:require [clojure.java.shell]))

(defn pre-run-time-preparations!
  [& _]
  (prn "[HS-UI] preparation")
  (clojure.java.shell/sh "bb" "tailwind-release"))
