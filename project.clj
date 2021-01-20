(defproject docker-controller "1.1.0"
  :description "An application to programmatically manage docker containers"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.0"]
                 [org.clojure/data.json "1.0.0"]
                 [unixsocket-http "1.0.5"]]
  :plugins      [[lein-ring "0.12.5"]]
  :ring {:handler docker-controller.handler/app
         :init    docker-controller.config/init
         :port    3000}
  :uberjar-name "docker-controller.jar"
  :profiles
  {:uberjar    {:aot :all}
   :production {:ring {:open-browser? false
                       :stacktraces?  false
                       :auto-reload?  false}}
   :dev        {:dependencies [[javax.servlet/servlet-api "2.5"]
                               [ring/ring-mock "0.3.2"]]
                :ring {:open-browser? false
                       :nrepl {:start? true}}}}
  :repl-options {:init-ns docker-controller.handler})
