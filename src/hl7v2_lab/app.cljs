(ns hl7v2-lab.app
  (:require
   [re-frame.core :as re :refer [subscribe]]
   [hl7v2-lab.inspector.views :refer [inspector]]))

(re/reg-event-db
 ::init-db
 (fn [_ _]
   { }))

(defn home []
  [:p
   "welcome home mom!"])

(defn not-found []
  [:p
   "not found"])

(defn index []
  [:div.container
   (if-let [view @(subscribe [:hl7v2-lab.routing/active-view])]
     (case view
       :home [home]
       :inspector [inspector]
       [not-found])
     [not-found])])
