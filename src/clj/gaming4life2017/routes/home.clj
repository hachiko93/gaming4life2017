(ns gaming4life2017.routes.home
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]))

(defn authenticated? [session]
  (if (or (nil? session) (nil? (:identity session)))
    false
    true))

(defn home-page [{:keys [flash]}]
  (layout/render "home.html"
                 (merge (select-keys flash [:message]))))

(defn about-page [{:keys [flash]}]
  (layout/render "about.html"
                 (merge (select-keys flash [:message]))))

(defroutes home-routes
  (GET "/home" request (home-page request))
  (GET "/about" request (about-page request)))
