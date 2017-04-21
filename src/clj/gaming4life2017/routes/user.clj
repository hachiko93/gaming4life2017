(ns gaming4life2017.routes.user
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes context GET POST]]
            [ring.util.http-response :as response]
            [gaming4life2017.db.core :as db]
            [gaming4life2017.routes.home :as home]
            [buddy.hashers :as hashers]
            [ring.util.response :refer [redirect]]))

(defn change-profile-picture [{:keys [params] session :session}]
  (db/change-profile-picture
    (assoc params :email (:identity session)))
  (response/found "/user"))

(defn change-about-me [{:keys [params] session :session}]
  (db/change-about-me
    (assoc params :email (:identity session)))
  (response/found "/user"))

(defn change-pass [{:keys [params] session :session}]
  (db/change-pass
    (->(assoc params :email (:identity session))
       (assoc :pass (hashers/encrypt (:pass params)))))
  (->(response/found (:location params))
     (assoc :flash (assoc params :message {:message "Password successfully changed!", :error false}))))

(defn user-page [{:keys [session flash]}]
  (if-not (home/authenticated? session)
    (redirect "/")
    (layout/render
      "user.html"
      (merge {:user (db/get-user-by-email
                      (hash-map :email (:identity session)))}
             (select-keys flash [:message])))))

(defroutes user-routes
  (context "/user" request
           (GET "/" [] (user-page request))
           (POST "/changeprofilepicture" [] (change-profile-picture request))
           (POST "/changeaboutme" [] (change-about-me request))
           (POST "/changepass" [] (change-pass request))))
