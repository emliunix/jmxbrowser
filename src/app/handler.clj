(ns app.handler
  (:require [app.jmx :as jmx]
            [org.httpkit.server :refer [with-channel send! close]]
            [cheshire.core :refer [generate-string]]
            [clojure.java.io :as io])
  (:import java.io.InputStream
           java.nio.ByteBuffer
           java.nio.file.Paths))

(defn ok-json [obj]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (generate-string {:ok true
                           :result obj})})

(defn get-domains [req]
  (->> (jmx/get-domains)
       (into [])
       ok-json))

(defn get-mbeans-in-domain [req]
  (->> (get-in req [:route-params :domain])
       (jmx/get-mbeans-under-domain)
       ok-json))

(defn get-hello [req]
  (let [guy (get-in req [:route-params :name] "somebody")]
    (ok-json {:message (str "Hello " guy "!!!")})))

(defn read-file-bytes [path]
  (let [f (io/file path)
        bs (byte-array (.length f))
        is (io/input-stream f)]
    (.read is bs)
    (.close is)
    bs))

(defn files [req]
  (let [file (get-in req [:route-params :file] "404.html")]
    (with-channel req chan
      (send! chan (slurp (str "pages/" file))))))

