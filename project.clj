(defproject docker-controller "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [http-kit "2.4.0-alpha6"]]

  :ring {:handler docker-controller.handler/app
         :init docker-controller.config/init}
  :profiles
  {:uberjar {:aot :all}
   :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]
         :plugins [[lein-ring "0.12.5"]]}}
  :repl-options {:init-ns docker-controller.core}
  :main docker-controller.core)
