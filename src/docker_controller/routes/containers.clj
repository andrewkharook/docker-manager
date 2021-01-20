(ns docker-controller.routes.containers
  "Container-related routes"
  (:require [unixsocket-http.core :as http]
            [clojure.data.json :as json]
            [clojure.spec.alpha :as spec]
            [docker-controller.config :refer [config]]
            [docker-controller.routes.service :refer [error format-response]]))

(def socket (http/client (:unix-socket @config)))

(def base-url "http://docker")

(defn list-all
  [_]
  (try
    (let [url (str base-url "/containers/json")
          response (http/get socket url)]
      (assoc response
        :headers {"content-type" "application/json"}
        :body (json/write-str
                (mapv #(select-keys % [:Id :Names])
                      (json/read-str (:body response) :key-fn keyword)))))
    (catch Exception e
      (ex-data e))))

(defn- start
  [container]
  (try
    (let [url (str base-url "/containers/" container "/start")]
      (format-response (http/post socket url)))
    (catch Exception e
      (ex-data e))))

(defn- stop
  [container]
  (try
    (let [url (str base-url "/containers/" container "/stop?t=5")]
      (format-response (http/post socket url)))
    (catch Exception e
      (ex-data e))))

(defn- restart
  [container]
  (try
    (let [url (str base-url "/containers/" container "/restart")]
      (format-response (http/post socket url)))
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

(defn status
  [request]
  (try
    (let [container (-> request :params :name)
          url (str base-url "/containers/" container "/json")
          response (http/get socket url)]
      (assoc response
        :headers {"content-type" "application/json"}
        :body (json/write-str {:status (let [status (:Status (:State (json/read-str (:body response) :key-fn keyword)))]
                                         (if (not= status "running")
                                           "exited"
                                           status))})))
    (catch Exception e
      (ex-data e))))