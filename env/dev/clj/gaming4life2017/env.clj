(ns gaming4life2017.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [gaming4life2017.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[gaming4life2017 started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[gaming4life2017 has shut down successfully]=-"))
   :middleware wrap-dev})
