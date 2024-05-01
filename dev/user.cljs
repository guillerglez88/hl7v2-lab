(ns user
  (:require
   [re-frame.db :refer [app-db]]
   [hl7v2-lab.core :refer [render-app]]))

(defn rerender []
  (render-app))

(comment

  (rerender)

  @app-db

  :cljs/quit

  :.)
