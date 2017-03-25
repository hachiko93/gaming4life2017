(ns gaming4life2017.routes.home
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [gaming4life2017.db.core :as db]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [ring.util.response :refer [redirect]]))

;; methods
(defn validate-params [params]
  (first
    (b/validate
      params
      :name v/required
      :description [v/required [v/min-count 10]]
      :type v/required
      :price v/required
      :picture v/required)))

(defn save-product [{:keys [params]}]
  (do
    (db/save-product params)
    (response/found "/products")))

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

(defn games-page []
  (layout/render "games.html"))

(defn user-page []
  (layout/render "user.html"))

(defroutes home-routes
  (GET "/" [] (login-page))
  (GET "/home" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/cart" [] (cart-page))
  (GET "/contact" [] (contact-page))
  (GET "/products" [] (products-page))
  (POST "/addproduct" request (save-product request))
  (GET "/games" [] (games-page))
  (GET "/user" [] (user-page)))

