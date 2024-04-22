(ns hl7v2-lab.core
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as rd]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [clojure.pprint :as pp]))

(defn home-page []
  [:div
   "Hi mom!"])

(def routes
  ["/" {:name ::home
        :view home-page}])

(defn init []
  (let [route-match (r/atom nil)
        init-router (fn []
                      (rfe/start!
                       (rf/router routes)
                       (fn [match]
                         (when match
                           (reset! route-match
                                   (if-let [cs (:controllers @route-match)]
                                     (assoc match :controllers (rfc/apply-controllers cs match))
                                     match))))
                       {:use-fragment true}))
        app (fn []
              (if @route-match
                (let [view (get-in @route-match [:data :view])]
                  [view @route-match])
                [:pre
                 (with-out-str
                   (pp/pprint @route-match))]))]
    (init-router)
    (rd/render
     (rd/create-root (.getElementById js/document "app"))
     [app])))
