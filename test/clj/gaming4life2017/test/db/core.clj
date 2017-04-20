(ns gaming4life2017.test.db.core
  (:require [gaming4life2017.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [gaming4life2017.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'gaming4life2017.config/env
      #'gaming4life2017.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-user
               {:first_name "Admin"
                :last_name  "Admin"
                :email      "clojureapp123@gmail.com"
                :pass       "admin"
                :admin      true})))
    (is (= {:first_name       "Admin"
            :last_name        "Admin"
            :email            "clojureapp123@gmail.com"
            :pass             "admin"
            :admin            true
            :about_me         nil
            :profile_picture  nil}
           (dissoc (db/get-user-by-email {:email "clojureapp123@gmail.com"}) :id)))))

(deftest test-products
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/save-product
               {:name         "Product1"
                :description  "Product1"
                :type         1
                :price        10
                :picture      nil})))
    (is (= {:name             "Product1"
            :description      "Product1"
            :type             "1"
            :price            10
            :picture          nil}
           (dissoc
             (->(db/get-products)
                (first)) :id)))))
