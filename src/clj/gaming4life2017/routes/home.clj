(ns gaming4life2017.routes.home
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [gaming4life2017.db.core :as db]
            [ring.util.response :refer [redirect]]
            [buddy.hashers :as hashers]
            [buddy.auth.accessrules :refer [restrict]]
            [postal.core :refer [send-message]]))

;; send email
(def email "clojureapp123@gmail.com")

(def conn {:host "smtp.gmail.com"
           :ssl true
           :user email
           :pass "klozurapp123"})

(defn send-email [{:keys [params]}]
  (do
    (do
      (send-message conn {:from email
                          :to email
                          :subject
                          (str "Contact from "
                               (params :name)
                               " on: Gaming4Life")
                          :body
                          (str (params :name)
                               " (email adress: "
                               (params :email)
                               " ) wrote: \n"
                               (params :message))})
      (send-message conn {:from email
                          :to (params :email)
                          :subject "Your contact message was successfully received"
                          :body "Gaming4Life received your contact message and will answer you as soon as possible. \n Please be patiend and wait for our response"}))
    (response/found "/contact")))

;; methods
(defn save-product [{:keys [params]}]
  (do
    (db/save-product params)
    (response/found "/products")))

(defn delete-product [{:keys [params]}]
  (do
    (db/delete-product params)
    (response/found "/products")))

(defn search-products [{:keys [params]}]
  (layout/render
    "products.html"
    (merge {:types (db/get-types)}
           {:products (db/search-products params)})))

(defn is-in-session [{user :user :as req}]
  (not (nil? user)))


(defn wrap-user [handler]
  (fn [{email :identity :as req}]
    (handler (assoc req :user (db/get-user-by-email email)))))

;; moze bolje
(defn login [{:keys [params] session :session :as req}]
  (if (and (some?
             (db/get-user-by-email params))
           (hashers/check
             (params :pass)
             ((db/get-user-by-email params) :pass)))
    (do
      (db/login params)
      (->(response/found "/home")
         (assoc :session (assoc session :identity (:email params)))))
    (-> (response/found "/")
        (assoc :flash (assoc params :message "Incorrect email or password")))))

(defn register [{:keys [params]}]
  (if-not (nil? (db/get-user-by-email params))
    (-> (response/found "/")
        (assoc :flash (assoc params :message "User with the same name already exists")))
    (do
      (db/create-user
        (assoc params :pass (hashers/encrypt (params :pass))))
      (->(response/found "/")
         (assoc :flash (assoc params :message "Successfully registrated"))))))

(defn add-to-cart [{:keys [params]}]
  (do
    (db/add-to-cart params))
  (response/found "/products"))

(defn change-profile-picture [{:keys [params]}]
  (do
    (db/change-profile-picture params))
  (response/found "/user"))

;; pages definition
(defn home-page []
  (layout/render "home.html"))

(defn login-page [{:keys [flash]}]
  (layout/render "login.html"
                 (merge (select-keys flash [:message]))))

(defn about-page []
  (layout/render "about.html"))

(defn cart-page []
  (layout/render
    "cart.html"
    (merge
      {:products (db/get-cart-products)})))

(defn contact-page []
  (layout/render "contact.html"))

(defn products-page [{:keys [params]}]
  (layout/render
    "products.html"
    (merge {:types (db/get-types)}
           {:products
            (if (empty? params) (db/get-products)
              (db/get-products-params
                (assoc params :type
                  (clojure.string/upper-case (params :type)))))})))

(defn user-page [{:keys [session]}]
  (layout/render
    "user.html"
    (merge {:user (db/get-user-by-email
                    (hash-map :email (session :identity)))})))

(defn logout [{session :session}]
  (-> (redirect "/")
      (assoc :session (dissoc session :identity))))

(defroutes home-routes
  (GET "/" request (login-page request))
  (GET "/home" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/cart" [] (cart-page))
  (GET "/contact" [] (contact-page))
  (GET "/products" request (products-page request))
  (GET "/user" request (user-page request))
  (GET "/logout" request (logout request))
  (POST "/searchproduct" request [] (search-products request))
  (POST "/addproduct" request (save-product request))
  (POST "/deleteproduct" request (delete-product request))
  (POST "/sendemail" request (send-email request))
  (POST "/addtocart" request (add-to-cart request))
  (POST "/changeprofilepicture" request (change-profile-picture request))
  (POST "/login" request (login request))
  (POST "/register" request (register request)))

