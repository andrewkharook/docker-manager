(ns docker-controller.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [docker-controller.routes.containers :as containers]))

(defroutes app-routes
           (context "/containers" []
             (GET "/" [] containers/list)
             (POST "/:id" [] containers/update))
           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))
