(ns docker-controller.routes.containers
  (:require [org.httpkit.client :as http]
            [docker-controller.config :refer [config]]))

(def url
  (str "unix://" (:unix-socket @config) "/" (:docker-api-version @config) "/containers/json"))

(defn list
  [request]
  (let [{:keys [status headers body error] :as resp} @(http/get url)]
    (if error
      (println "Failed, exception: " error)
      (println "HTTP GET success: " status))))

(defn update
  [request]
  (let [container (-> request :params :id)]))

(defn- start
  [container]
  )

(defn- stop
  [container]
  )