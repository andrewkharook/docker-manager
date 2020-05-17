(ns docker-controller.routes.service
  "Service routes"
  (:require [clojure.spec.alpha :as spec]
            [clojure.data.json :as json]))

(defn error
  ([text]
   (error text 500))
  ([text status]
   (if (spec/valid? int? status)
     {:status status
      :body   (json/write-str {:errors [{:status (str status)
                                         :detail text}]})}
     (throw (IllegalArgumentException. "Status code must be an integer.")))))