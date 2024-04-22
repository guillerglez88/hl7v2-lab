(ns hl7v2-lab.core
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as rd]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [clojure.pprint :as pp]))

(defonce match (r/atom nil))

(defn home-page []
  [:div
   "Hi mom!"])

(def router
  (rf/router
   ["/" {:name ::home
         :view home-page}]))

(defn app []
  (if @match
    (let [view (get-in @match [:data :view])]
      [view @match])
    [:pre
     (with-out-str
       (pp/pprint @match))]))

(defn init []
  (rfe/start!
   router
   (fn [new-match]
     (when new-match
       (reset! match
               (if-let [cs (:controllers @match)]
                 (assoc new-match :controllers (rfc/apply-controllers cs new-match))
                 new-match))))
   {:use-fragment true})
  (rd/render
   (rd/create-root (.getElementById js/document "app"))
   [app]))
