(ns app.handler
  (:require [app.jmx :as jmx]
            [cheshire.core :refer [generate-string]]))

(defn ok-json [obj]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (generate-string obj)})

(defn get-domains [req]
  (-> (jmx/get-domains)
      ok-json))

(defn get-hello [req]
  (let [guy (get-in [:route-params :name] "somebody")]
    (println (clojure.string/join "\n" (map name (keys req))))
    (println (clojure.string/join "\n" (map name (keys (:route-params req)))))
    (ok-json {:message (str "Hello " guy "!!!")})))
