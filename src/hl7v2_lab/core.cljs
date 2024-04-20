(ns hl7v2-lab.core
  (:require
   [reagent.dom :as rd]))

(defn app []
  [:div
   "Hi mom!"])

(defn init []
  (rd/render
   [app]
   (.getElementById js/document "app")))
