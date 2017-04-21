(ns gaming4life2017.routes.contact
  (:require [gaming4life2017.layout :as layout]
            [compojure.core :refer [defroutes context GET POST]]
            [ring.util.http-response :as response]
            [postal.core :refer [send-message]]))

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
                             (:name params)
                             " on: Gaming4Life")
                        :body
                        (str (:name params)
                             " (email adress: "
                             (:email params)
                             " ) wrote: \n"
                             (:message params))})
    (send-message conn {:from email
                        :to (:email params)
                        :subject "Your contact message was successfully received"
                        :body body}))
  (->(response/found "/contact")
     (assoc :flash (assoc params :message {:message "Message successfully sent", :error false}))))

(defn contact-page [{:keys [flash]}]
  (layout/render "contact.html"
                 (merge (select-keys flash [:message]))))

(defroutes contact-routes
  (context "/contact" request
           (GET "/" [] (contact-page request))
           (POST "/sendemail" [] (send-email request))))
