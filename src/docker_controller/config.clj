(ns docker-controller.config)

(def config
  (atom {:unix-socket "unix:///var/run/docker.sock"}))

(defn init
  []
  (println "Initializing application...")
  (if (some? (System/getenv "UNIX_SOCKET"))
    (swap! config assoc :unix-socket (System/getenv "UNIX_SOCKET")))
  (if (some? (System/getenv "TOKEN"))
    (swap! config assoc :token (System/getenv "TOKEN"))
    (println
      "\n"
      "*** WARNING! Running the application without security token leaves your docker API open to the world and is not recommended for production use!"
      "\n")))
