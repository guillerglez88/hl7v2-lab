(ns hl7v2-lab.core
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as rd]
   [re-frame.core :as re :refer [dispatch-sync]]
   [hl7v2-lab.routing :refer [init-routes]]
   [hl7v2-lab.app :refer [index]]))

(defonce root-component (r/atom nil))

(defn ^:dev/after-load render-app []
  (when-not @root-component
    (reset! root-component (rd/create-root (.getElementById js/document "app"))))
  (rd/render
   @root-component
   [index]))

(defn init []
  (when ^boolean goog.DEBUG
    (enable-console-print!)
    (println "--> dev mode"))
  (re/clear-subscription-cache!)
  (init-routes)
  (dispatch-sync [:hl7v2-lab.app/init-db])
  (render-app))
