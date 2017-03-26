(ns gaming4life2017.routes.home
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [gaming4life2017.db.core :as db]
            [ring.util.response :refer [redirect]]
            [postal.core :refer [send-message]]))

;; send email
(def email "clojureapp123@gmail.com")

(def conn {:host "smtp.gmail.com"
           :ssl true
           :user email
           :pass "klozurapp123"})

(defn send-email [{:keys [params]}]
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
                           (params :message))}))

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

(defn login [{:keys [params]}]
  (do
    (db/login params)
    (layout/render
      "home.html"
      (merge
        {:login true}))))

(defn register [{:keys [params]}]
  (do
    (db/create-user
      (assoc params :admin true))
    (layout/render
      "login.html"
      (merge
        {:message "Successfully registrated"}))))

(defn add-to-cart [{:keys [params]}]
  (do
    (db/add-to-cart
      (assoc params :user_id "1")))
  (response/found "/products"))

;; pages definition
(defn home-page []
  (layout/render "home.html"))

(defn login-page []
  (layout/render "login.html"))

(defn about-page []
  (layout/render "about.html"))

(defn cart-page []
  (layout/render "cart.html"))

(defn contact-page []
  (layout/render "contact.html"))

(defn products-page []
  (layout/render
    "products.html"
    (merge {:types (db/get-types)}
           {:products (db/get-products)})))

(defn user-page []
  (layout/render "user.html"))

(defroutes home-routes
  (GET "/" [] (login-page))
  (GET "/home" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/cart" [] (cart-page))
  (GET "/contact" [] (contact-page))
  (GET "/products" [] (products-page))
  (GET "/user" [] (user-page))
  (POST "/searchproduct" request [] (search-products request))
  (POST "/addproduct" request (save-product request))
  (POST "/deleteproduct" request (delete-product request))
  (POST "/sendemail" request (send-email request))
  (POST "/addtocart" request (add-to-cart request))
  (POST "/login" request (login request))
  (POST "/register" request (register request)))

