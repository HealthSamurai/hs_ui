(ns prep
  (:require [clojure.java.shell]))

(defn pre-run-time-preparations!
  [& _]
  (prn "[HS-UI] preparation")
  (let [^ProcessBuilder builder (java.lang.ProcessBuilder. ["make" "tailwind-release"])]
    (.redirectOutput builder java.lang.ProcessBuilder$Redirect/INHERIT)
    (.redirectError builder java.lang.ProcessBuilder$Redirect/INHERIT)
    (.waitFor (.start builder))))
