(ns gaming4life2017.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [gaming4life2017.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response ((app) (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
