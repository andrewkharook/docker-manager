(ns docker-controller.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [docker-controller.routes.containers :as containers]))

(defroutes app-routes
           (context "/containers" []
             (GET "/" [] containers/list-all)
             (POST "/:name" [] containers/change-state))
           (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-defaults api-defaults)))
