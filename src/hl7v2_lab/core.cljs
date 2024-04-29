(ns hl7v2-lab.core
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as rd]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]))

(defonce route-match (r/atom nil))
(defonce root-component (r/atom nil))

(defn app []
  (if-let [view (get-in @route-match [:data :view])]
    [view @route-match]
    "not-found"))

(defn home-page []
  [:div.container
   [:div.notification.is-primary
    "Hi mom!"]])

(def routes
  ["/" {:name ::home
        :view (fn []
                [home-page])}])

(defn init-routes []
  (rfe/start!
   (rf/router routes)
   (fn [match]
     (when match
       (reset! route-match
               (if-let [cs (:controllers @route-match)]
                 (assoc match :controllers (rfc/apply-controllers cs match))
                 match))))
   {:use-fragment true}))

(defn ^:dev/after-load render-app []
  (when-not @root-component
    (reset! root-component (rd/create-root (.getElementById js/document "app"))))
  (rd/render
   @root-component
   [app]))

(defn init []
  (when ^boolean goog.DEBUG
    (enable-console-print!)
    (println "--> dev mode"))
  (init-routes)
  (render-app))
