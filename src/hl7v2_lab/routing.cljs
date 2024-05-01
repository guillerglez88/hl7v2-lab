(ns hl7v2-lab.routing
  (:require
   [reitit.frontend.easy :as rfe]
   [reitit.frontend :as rf]
   [reitit.frontend.controllers :as rfc]
   [re-frame.core :as re :refer [dispatch]]))

(re/reg-event-fx
 ::navigate
 (fn [db [_ match]]
   {:db (update db :route-match (fn [prev-match]
                                  (when match
                                    (if-let [cs (:controlelrs prev-match)]
                                      (assoc match :controlelrs (rfc/apply-controllers cs match))
                                      match))))}))

(re/reg-sub
 ::active-view
 (fn [db _]
   (get-in db [:route-match :data :name])))

(def routes
  [["/" {:name :home}]
   ["/inspector" {:name :inspector}]])

(defn init-routes []
  (rfe/start!
   (rf/router routes)
   #(dispatch [::navigate %])
   {:use-fragment true}))
