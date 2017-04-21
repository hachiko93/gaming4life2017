(ns gaming4life2017.routes.product
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes context GET POST]]
            [ring.util.http-response :as response]
            [gaming4life2017.db.core :as db]
            [gaming4life2017.routes.home :as home]
            [ring.util.response :refer [redirect]]))

(defn save-product [{:keys [params]}]
  (do
    (db/save-product params)
    (response/found "/products")))

(defn delete-product [{:keys [params]}]
  (do
    (db/delete-product-from-all-carts {:product_id (:id params)})
    (db/delete-product params)
    (response/found "/products")))

(defn search-products [{:keys [params]}]
  (->(response/found "/products")
     (assoc :flash (assoc params :products (db/search-products params)))))

(defn products-page [{:keys [params flash] session :session}]
  (if-not (home/authenticated? session)
    (redirect "/")
    (layout/render
      "products.html"
      (merge {:types (db/get-types)}
             {:products
              (if (empty? params)
                (db/get-products)
                (db/get-products-params
                  (assoc params :type
                    (clojure.string/upper-case (:type params)))))}
             (select-keys flash [:message :products])
             {:admin (:admin session)}))))

(defroutes product-routes
  (context "/products" request
           (GET "/" [] (products-page request))
           (POST "/search" [] (search-products request))
           (POST "/add" [] (save-product request))
           (POST "/delete" [] (delete-product request))))
