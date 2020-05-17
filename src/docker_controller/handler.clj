(ns docker-controller.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [docker-controller.middleware.wrap-auth-token :refer [wrap-auth-token]]
            [docker-controller.routes.containers :as containers]
            [docker-controller.routes.service :refer [error]]))

(defroutes app-routes
           (context "/containers" []
             (GET "/" [] containers/list-all)
             (POST "/:name" [] containers/change-state))
           (route/not-found (error "Resource not found" 404)))

(def app
  (-> app-routes
      (wrap-auth-token)
      (wrap-json-body {:keywords? true})
      (wrap-defaults api-defaults)))
