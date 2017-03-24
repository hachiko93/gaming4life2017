(ns user
  (:require [mount.core :as mount]
            gaming4life2017.core))

(defn start []
  (mount/start-without #'gaming4life2017.core/http-server
                       #'gaming4life2017.core/repl-server))

(defn stop []
  (mount/stop-except #'gaming4life2017.core/http-server
                     #'gaming4life2017.core/repl-server))

(defn restart []
  (stop)
  (start))


