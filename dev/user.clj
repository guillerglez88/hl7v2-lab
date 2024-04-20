(ns user
  (:require
   [shadow.cljs.devtools.api :as shadow]
   [shadow.cljs.devtools.server :as server]))

(defn cljs-repl
  "Connects to a given build-id. Defaults to `:app`.
   docs: https://docs.cider.mx/cider/cljs/shadow-cljs.html#using-shadow-cljs-with-deps-edn-and-custom-repl-initialization"
  ([]
   (cljs-repl :app))
  ([build-id]
   (server/start!)
   (shadow/watch build-id)
   (shadow/nrepl-select build-id)))

(comment
  (cljs-repl)

  :.)
