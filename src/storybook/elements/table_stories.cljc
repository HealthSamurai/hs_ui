(ns storybook.elements.table-stories
  (:require
   [storybook.utils :as utils]
   [hs-ui.elements.table]))

(def ^:export default
  (clj->js
   {:title     "Elements/Table"
    :argTypes  {"class"        {:control "text"}
                "columns"      {:control "object"}
                "rows"         {:control "object"}
                "on-row-click" {:control "object"}
                "draggable"    {:control "boolean"}
                "visibility-ctrl" {:control "boolean"}}
    :render    (utils/storybook-render-default hs-ui.elements.table/view)}))

(def ^:export Basic
  (clj->js {:args {:columns [{:name "First Name"}
                             {:name "Last Name"}
                             {:name "Gender"}
                             {:name "Address"}]
                   :rows   [{"First Name" {:value "Christy"}
                             "Last Name"  {:value "Butterfield"}
                             "Gender"     {:value "Male"}
                             "Address"    {:value "3319 Nelm Street Centerville, VA 22020"}}
                            {"First Name" {:value "Carl"}
                             "Last Name"  {:value "Cortez"}
                             "Gender"     {:value "Male"}
                             "Address"    {:value "4353b Mill Street Avon Park, FL 33825"}}
                            {"First Name" {:value "Christy"}
                             "Last Name"  {:value "Wise"}
                             "Gender"     {:value "Female"}
                             "Address"    {:value "4532 Eva Pearl Street Baton Rouge, LA 70802"}}]}}))

(def ^:export DraggableWithVisibilityCtrl
  (clj->js {:args {:columns [{:name "First Name"}
                             {:name "Last Name"}
                             {:name "Gender"}
                             {:name "Address"}]
                   :rows    [{"First Name" {:value "Christy"}
                              "Last Name"  {:value "Butterfield"}
                              "Gender"     {:value "Male"}
                              "Address"    {:value "3319 Nelm Street Centerville, VA 22020"}}
                             {"First Name" {:value "Carl"}
                              "Last Name"  {:value "Cortez"}
                              "Gender"     {:value "Male"}
                              "Address"    {:value "4353 Mill Street Avon Park, FL 33825"}}
                             {"First Name" {:value "Christy"}
                              "Last Name"  {:value "Wise"}
                              "Gender"     {:value "Female"}
                              "Address"    {:value "4532 Eva Pearl Street Baton Rouge, LA 70802"}}]
                   :draggable true
                   :visibility-ctrl true}}))

(def ^:export CustomWidth
  (clj->js {:args {:on-row-click (fn [row] (prn row))
                   :columns [{:name "First Name"}
                             {:name "Last Name"}
                             {:name "Gender" :width "200px"}
                             {:name "Address"}]
                   :rows    [{"First Name" {:value "Christy"}
                              "Last Name"  {:value "Butterfield"}
                              "Gender"     {:value "Male"}
                              "Address"    {:value "3319 Nelm Street Centerville, VA 22020"}}
                             {"First Name" {:value "Carl"}
                              "Last Name"  {:value "Cortez"}
                              "Gender"     {:value "Male"}
                              "Address"    {:value "4353 Mill Street Avon Park, FL 33825"}}
                             {"First Name" {:value "Christy"}
                              "Last Name"  {:value "Wise"}
                              "Gender"     {:value "Female"}
                              "Address"    {:value "4532 Eva Pearl Street Baton Rouge, LA 70802"}
                              :selected? true}]}}))
