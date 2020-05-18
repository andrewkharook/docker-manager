(ns docker-controller.middleware.wrap-auth-token
  "Verify authentication token."
  (:require [docker-controller.config :refer [config]]
            [docker-controller.routes.service :refer [error]]))

(defn wrap-auth-token
  "Middleware that compares the auth token from request to the one defined in config."
  [handler]
  (fn [req]
    (let [req-token (or (:token (:params req))
                        (:token (:body req)))]
      (if (= req-token (:token @config))
        (handler req)
        (error "Access denied" 403)))))