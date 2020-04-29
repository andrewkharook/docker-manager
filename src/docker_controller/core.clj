(ns docker-controller.core
  (:require [org.httpkit.server :refer [run-server]]
            [docker-controller.config :refer [config]]
            [docker-controller.handler :as handler])
  (:gen-class))

(defn -main
  [& args]
  (run-server handler/app {:port (:port @config)
                           :init docker-controller.config/init}))
