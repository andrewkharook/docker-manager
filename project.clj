(defproject docker-controller "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
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
  :profiles
  {:uberjar    {:aot :all}
   :production {:ring {:open-browser? false,
                       :stacktraces?  false,
                       :auto-reload?  false}}
   :dev        {:dependencies [[javax.servlet/servlet-api "2.5"]
                               [ring/ring-mock "0.3.2"]]}}
  :repl-options {:init-ns docker-controller.handler})
