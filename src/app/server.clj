(ns app.server
  (:require [org.httpkit.server :refer [run-server]]
            [bidi.ring :refer [make-handler]]
            [app.handler :refer [get-domains get-hello]]))

(def routes
  ["/" {"domains" get-domains
        ["domain/" :name] :hello
        ["name/" :name] get-hello}])

(def handler
  (make-handler routes))

(defonce server (atom nil))

(defn start-server []
  (if-let [s @server]
    (s {:timeout 1000})
    (reset! server (run-server handler {:port 8888}))))

(defn stop-server []
  (if-let [s @server]
    (s {:timeout 1000}))
  (reset! server nil))
