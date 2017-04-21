(ns gaming4life2017.routes.auth
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [gaming4life2017.db.core :as db]
            [ring.util.response :refer [redirect]]
            [buddy.hashers :as hashers]))

(defn login [{:keys [params] session :session}]
  (let [user (db/get-user-by-email params)]
    (if (and (some? user)
             (hashers/check
               (:pass params)
               (:pass user)))
      (do
        (db/login params)
        (->(response/found "/home")
           (assoc :session
             (->(assoc session :identity (:email params))
                (assoc :admin (:admin user))))))
      (-> (response/found "/")
          (assoc :flash (assoc params :message {:message "Incorrect email or password", :error true}))))))

(defn logout [{session :session}]
  (-> (redirect "/")
      (assoc :session (dissoc session :identity))))

(defn register [{:keys [params]}]
  (if-not (nil? (db/get-user-by-email params))
    (-> (response/found "/")
        (assoc :flash (assoc params :message {:message "User with the same name already exists", :error true})))
    (do
      (db/create-user
        (->(update-in params [:pass] hashers/encrypt)
           (assoc :admin false)))
      (->(response/found "/")
         (assoc :flash (assoc params :message {:message "Successfully registrated", :error false}))))))

(defn login-page [{:keys [flash]}]
  (layout/render "login.html"
                 (merge (select-keys flash [:message]))))

(defroutes auth-routes
  (GET "/" request (login-page request))
  (POST "/login" request (login request))
  (GET "/logout" request (logout request))
  (POST "/register" request (register request)))
