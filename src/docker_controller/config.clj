(ns docker-controller.config)

(def config
  (atom {:unix-socket "unix:///var/run/docker.sock"}))

(defn init
  []
  (println "Initializing application...")
  (if (some? (System/getenv "UNIX_SOCKET"))
    (swap! config assoc :unix-socket (System/getenv "UNIX_SOCKET"))))
