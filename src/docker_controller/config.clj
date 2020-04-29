(ns docker-controller.config)

(def config
  (let [port (if (some? (System/getenv "port"))
               (Integer. (System/getenv "port"))
               3000)]
   (atom {:port port})))

(defn init
  "Initialize application with runtime variables"
  []
  (println "Initializing application...")
  (swap! config
         assoc
         :unix-socket (System/getenv "unix-socket")
         :docker-api-version "1.40"))
