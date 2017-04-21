(ns gaming4life2017.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [gaming4life2017.layout :refer [error-page]]
            [gaming4life2017.routes.auth :refer [auth-routes]]
            [gaming4life2017.routes.cart :refer [cart-routes]]
            [gaming4life2017.routes.contact :refer [contact-routes]]
            [gaming4life2017.routes.home :refer [home-routes]]
            [gaming4life2017.routes.product :refer [product-routes]]
            [gaming4life2017.routes.user :refer [user-routes]]
            [compojure.route :as route]
            [gaming4life2017.env :refer [defaults]]
            [mount.core :as mount]
            [gaming4life2017.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'auth-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'contact-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'cart-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'user-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'product-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
