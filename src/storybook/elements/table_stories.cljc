(ns storybook.elements.table-stories
  (:require
   [storybook.utils :as utils]
   [hs-ui.elements.table]))

(def ^:export default
  (clj->js
   {:title     "Elements/Table"
    :argTypes  {"class"    {:control "text"}
                "columns"  {:control "object"}
                "items"    {:control "object"}}
    :render    (utils/storybook-render-default hs-ui.elements.table/view)}))

(def ^:export Basic
  (clj->js {:args {:columns [{:name "First Name"}
                             {:name "Last Name"}
                             {:name "Gender"}
                             {:name "Address"}]
                   :items   [{"First Name" "Christy"
                              "Last Name"  "Butterfield"
                              "Gender"     "Male"
                              "Address"    "3319 Nelm Street Centerville, VA 22020"}
                             {"First Name" "Carl"
                              "Last Name"  "Cortez"
                              "Gender"     "Male"
                              "Address"    "4353 Mill Street Avon Park, FL 33825"}
                             {"First Name" "Christy"
                              "Last Name"  "Wise"
                              "Gender"     "Female"
                              "Address"    "4532 Eva Pearl Street Baton Rouge, LA 70802"}]}}))

(def ^:export CustomWidth
  (clj->js {:args {:columns [{:name "First Name"}
                             {:name "Last Name"}
                             {:name "Gender" :width "100px"}
                             {:name "Address"}]
                   :items   [{"First Name" "Christy"
                              "Last Name"  "Butterfield"
                              "Gender"     "Male"
                              "Address"    "3319 Nelm Street Centerville, VA 22020"}
                             {"First Name" "Carl"
                              "Last Name"  "Cortez"
                              "Gender"     "Male"
                              "Address"    "4353 Mill Street Avon Park, FL 33825"}
                             {"First Name" "Christy"
                              "Last Name"  "Wise"
                              "Gender"     "Female"
                              "Address"    "4532 Eva Pearl Street Baton Rouge, LA 70802"}]}}))
