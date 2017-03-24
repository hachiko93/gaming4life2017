(ns gaming4life2017.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[gaming4life2017 started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[gaming4life2017 has shut down successfully]=-"))
   :middleware identity})
