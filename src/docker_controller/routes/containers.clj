(ns docker-controller.routes.containers
  "Container-related routes"
  (:require [unixsocket-http.core :as http]
            [clojure.data.json :as json]
            [clojure.spec.alpha :as spec]
            [docker-controller.config :refer [config]]
            [docker-controller.routes.service :refer [error]]))

(def socket (http/client (:unix-socket @config)))

(def base-url "http://docker")

(defn list-all
  [_]
  (try
    (let [url (str base-url "/containers/json")]
      (json/write-str
        (mapv #(select-keys % [:Id :Names])
              (json/read-str (:body (http/get socket url)) :key-fn keyword))))
    (catch Exception e
      (ex-data e))))

(defn- start
  [container]
  (try
    (let [url (str base-url "/containers/" container "/start")]
      (http/post socket url))
    (catch Exception e
      (ex-data e))))

(defn- stop
  [container]
  (try
    (let [url (str base-url "/containers/" container "/stop?t=5")]
      (http/post socket url))
    (catch Exception e
      (ex-data e))))

(defn- restart
  [container]
  (try
    (let [url (str base-url "/containers/" container "/restart")]
      (http/post socket url))
    (catch Exception e
      (ex-data e))))

(defn change-state
  [request]
  (let [container (-> request :params :name)
        action (spec/conform #{"start" "stop" "restart"} (-> request :body :action))]
    (case action
      "start" (start container)
      "stop" (stop container)
      "restart" (restart container)
      ::spec/invalid (error "Bad request" 400))))
