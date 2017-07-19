(ns app.server
  (:require [org.httpkit.server :refer [run-server]]
            [bidi.ring :refer [make-handler]]
            [app.handler :refer [get-domains
                                 get-mbeans-in-domain
                                 get-hello
                                 files]]))

(def routes
  ["/" {"domains" get-domains
        ["domain/" :domain] get-mbeans-in-domain
        ["name/" :name] get-hello
        ["pages/" :file] files}])

(def handler
  (make-handler routes))

(defn auto-reload-handler [req]
  (@(var handler) req))

(defonce server (atom nil))

(defn start-server []
  (if-let [s @server]
    (s {:timeout 1000})
    (reset! server (run-server auto-reload-handler {:port 8888}))))

(defn stop-server []
  (if-let [s @server]
    (s :timeout 1000))
  (reset! server nil))
