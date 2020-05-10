(ns docker-controller.routes.containers
  (:require [unixsocket-http.core :as http]
            [clojure.data.json :as json]
            [clojure.spec.alpha :as spec]
            [docker-controller.config :refer [config]]
            [docker-controller.response :refer [response]]))

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

(defn change-state
  [request]
  (let [container (-> request :params :name)
        action (spec/conform #{"start" "stop"} (-> request :body :action))]
    (case action
      "start" (start container)
      "stop" (stop container)
      ::spec/invalid {:status 400
                      :body   (json/write-str {:error "Bad request"})})))