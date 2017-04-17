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

(def body "Gaming4Life received your contact message and will answer you as soon as possible. \n Please be patiend and wait for our response")

(defn send-email [{:keys [params]}]
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
                        :body body}))
  (->(response/found "/contact")
     (assoc :flash (assoc params :message
                     (hash-map :message "Message successfully sent", :error false)))))

(defn save-product [{:keys [params]}]
  (do
    (db/save-product params)
    (response/found "/products")))

(defn delete-product [{:keys [params]}]
  (do
    (db/delete-product params)
    (response/found "/products")))

(defn search-products [{:keys [params]}]
  (->(response/found "/products")
     (assoc :flash (assoc params :products (db/search-products params)))))

(defn login [{:keys [params] session :session}]
  (def user
    (db/get-user-by-email params))
  (if (and (some? user)
           (hashers/check
             (params :pass)
             (user :pass)))
    (do
      (db/login params)
      (->(response/found "/home")
         (assoc :session (assoc session :identity (:email params)))))
    (-> (response/found "/")
        (assoc :flash (assoc params :message
                        (hash-map :message "Incorrect email or password", :error true))))))

(defn register [{:keys [params]}]
  (if-not (nil? (db/get-user-by-email params))
    (-> (response/found "/")
        (assoc :flash (assoc params :message
                        (hash-map :message "User with the same name already exists", :error true))))
    (do
      (db/create-user
        (assoc params :pass (hashers/encrypt (params :pass))))
      (->(response/found "/")
         (assoc :flash (assoc params :message
                         (hash-map :message "Successfully registrated", :error false)))))))

(defn add-to-cart [{:keys [params] session :session}]
  (do
    (def db-params
      (assoc params :user_id
        ((db/get-user-by-email
           (hash-map :email (session :identity))) :id)))
    (def product
      (db/get-product-from-cart db-params))
    (if (empty? product)
      (db/add-to-cart db-params)
      (db/update-cart db-params))
    (->(response/found "/products")
       (assoc :flash (assoc params :message
                       (hash-map :message "Successfully added to cart", :error false))))))

(defn clear-cart[{session :session}]
  (db/clear-user-cart
    (hash-map :user_id
              ((db/get-user-by-email
                 (hash-map :email (session :identity)))
               :id)))
  (response/found "/cart"))

(defn cart-checkout[{:keys [params] session :session}]
  (db/clear-user-cart
    (hash-map :user_id
              ((db/get-user-by-email
                 (hash-map :email (session :identity)))
               :id)))
  (-> (response/found "/cart")
      (assoc :flash (assoc params :message
                      (hash-map :message "Products successfully purchased", :error false)))))

(defn change-profile-picture [{:keys [params] session :session}]
  (do
    (db/change-profile-picture
      (assoc params :email
        (session :identity))))
  (response/found "/user"))

(defn change-about-me [{:keys [params] session :session}]
  (do
    (db/change-about-me
      (assoc params :email
        (session :identity))))
  (response/found "/user"))

(defn change-pass [{:keys [params] session :session}]
  (do
    (db/change-pass
      (assoc (assoc params :email
               (session :identity)) :pass (hashers/encrypt (params :pass)))))
  (->(response/found (params :location))
     (assoc :flash (assoc params :message
                     (hash-map :message "Password successfully changed!", :error false)))))

(defn remove-from-cart [{:keys [params] session :session}]
  (do
    (db/delete-from-cart
      (assoc params :user_id
        ((db/get-user-by-email
           (hash-map :email (session :identity)))
         :id)))
    (->(response/found "/cart")
       (assoc :flash (assoc params :message
                       (hash-map :message "Product successfully removed", :error false))))))

;; pages definition
(defn home-page [{:keys [flash]}]
  (layout/render "home.html"
                 (merge (select-keys flash [:message]))))

(defn login-page [{:keys [flash]}]
  (layout/render "login.html"
                 (merge (select-keys flash [:message]))))

(defn about-page [{:keys [flash]}]
  (layout/render "about.html"
                 (merge (select-keys flash [:message]))))

(defn cart-page [{:keys [flash] session :session}]
  (layout/render
    "cart.html"
    (merge
      {:products (db/get-cart-products
                   (hash-map :user_id
                             ((db/get-user-by-email (hash-map :email (session :identity))) :id)))}
      (select-keys flash [:message]))))

(defn contact-page [{:keys [flash]}]
  (layout/render "contact.html"
                 (merge (select-keys flash [:message]))))

(defn products-page [{:keys [params flash]}]
  (layout/render
    "products.html"
    (merge {:types (db/get-types)}
           {:products
            (if (empty? params)
              (db/get-products)
              (db/get-products-params
                (assoc params :type
                  (clojure.string/upper-case (params :type)))))}
           (select-keys flash [:message :products]))))

(defn user-page [{:keys [session flash]}]
  ;;   ispravi ako bude moglo
  (if (or (nil? session) (nil? (session :identity)))
    (redirect "/")
    (layout/render
      "user.html"
      (merge {:user (db/get-user-by-email
                      (hash-map :email (session :identity)))}
             (select-keys flash [:message])))))

(defn logout [{session :session}]
  (-> (redirect "/")
      (assoc :session (dissoc session :identity))))

(defroutes home-routes
  (GET "/" request (login-page request))
  (GET "/home" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/cart" request (cart-page request))
  (POST "/cart/remove" request (remove-from-cart request))
  (POST "/cart/add" request (add-to-cart request))
  (GET "/cart/clear" request (clear-cart request))
  (GET "/cart/checkout" request (cart-checkout request))
  (GET "/contact" request (contact-page request))
  (GET "/products" request (products-page request))
  (GET "/user" request (user-page request))
  (GET "/logout" request (logout request))
  (POST "/searchproduct" request [] (search-products request))
  (POST "/addproduct" request (save-product request))
  (POST "/deleteproduct" request (delete-product request))
  (POST "/sendemail" request (send-email request))
  (POST "/changeprofilepicture" request (change-profile-picture request))
  (POST "/changeaboutme" request (change-about-me request))
  (POST "/changepass" request (change-pass request))
  (POST "/login" request (login request))
  (POST "/register" request (register request)))
