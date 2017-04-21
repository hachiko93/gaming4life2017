(ns gaming4life2017.routes.cart
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes context GET POST]]
            [ring.util.http-response :as response]
            [gaming4life2017.db.core :as db]
            [gaming4life2017.routes.home :as home]
            [ring.util.response :refer [redirect]]))

(defn add-to-cart [{:keys [params] session :session}]
  (let [db-params (doto (assoc params :user_id (:id (db/get-user-by-email (hash-map :email (:identity session))))))
        product (db/get-product-from-cart db-params)]
    (if (empty? product)
      (db/add-to-cart db-params)
      (db/update-cart db-params))
    (->(response/found "/products")
       (assoc :flash (assoc params :message {:message "Successfully added to cart", :error false})))))

(defn remove-from-cart [{:keys [params] session :session}]
  (db/delete-from-cart
    (assoc params :user_id
      (:id (db/get-user-by-email
             (hash-map :email (:identity session))))))
  (->(response/found "/cart")
     (assoc :flash (assoc params :message {:message "Product successfully removed", :error false}))))

(defn cart-checkout[{:keys [params] session :session}]
  (db/clear-user-cart
    (hash-map :user_id
              (:id (db/get-user-by-email
                     (hash-map :email (:identity session))))))
  (->(response/found "/cart")
     (assoc :flash (assoc params :message {:message "Products successfully purchased", :error false}))))

(defn clear-cart[{session :session}]
  (db/clear-user-cart
    (hash-map :user_id
              (:id (db/get-user-by-email (hash-map :email (:identity session))))))
  (response/found "/cart"))

(defn cart-page [{:keys [flash] session :session}]
  (if-not (home/authenticated? session)
    (redirect "/")
    (layout/render
      "cart.html"
      (merge
        {:products (db/get-cart-products
                     (hash-map :user_id
                               (:id (db/get-user-by-email (hash-map :email (:identity session))))))}
        (select-keys flash [:message])))))

(defroutes cart-routes
  (context "/cart" request
           (GET "/" [] (cart-page request))
           (GET "/clear" [] (clear-cart request))
           (GET "/checkout" [] (cart-checkout request))
           (POST "/remove" [] (remove-from-cart request))
           (POST "/add" [] (add-to-cart request))))
