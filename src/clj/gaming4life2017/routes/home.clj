(ns gaming4life2017.routes.home
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [gaming4life2017.db.core :as db]))

(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defn cart-page []
  (layout/render "cart.html"))

(defn changepassword-page []
  (layout/render "changepassword.html"))

(defn contact-page []
  (layout/render "contact.html"))

(defn products-page []
  (layout/render "products.html"))

(defn login-page []
  (layout/render "login.html"))

(defn games-page []
  (layout/render "games.html"))

(defn user-page []
  (layout/render "user.html"))

(defroutes home-routes
  (GET "/" [] (login-page))
  (GET "/home" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/cart" [] (cart-page))
  (GET "/changepassword" [] (changepassword-page))
  (GET "/contact" [] (contact-page))
  (GET "/products" [] (products-page))
  (GET "/login" [] (login-page))
  (GET "/games" [] (games-page))
  (GET "/user" [] (user-page)))

