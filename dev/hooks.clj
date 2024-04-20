(ns hooks
  (:require
   [clojure.string :as str]))

(defn update-hashed-modules
  {:shadow.build/stage :flush}
  [build-stage & {:keys [html-file manifest-file] :as opts}]
  (println "Updating hashed modules" opts)
  (spit html-file
        (reduce
         (fn [html {:keys [module-id output-name]}]
           (let [js (str (name module-id) ".js")]
             (printf "replacing: %s --> %s\n" js output-name)
             (str/replace html js output-name)))
         (slurp html-file)
         (read-string (slurp manifest-file))))
  build-stage)

(comment
  (update-hashed-modules {}
                         :html-file "resources/public/index.html"
                         :manifest-file "resources/public/js/manifest.edn")

  :.)
