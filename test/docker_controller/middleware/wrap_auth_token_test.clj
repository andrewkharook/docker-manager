(ns docker-controller.middleware.wrap-auth-token-test
  (:require [clojure.test :refer :all]
            [docker-controller.middleware.wrap-auth-token :refer :all]
            [docker-controller.config :refer [config]]))

(def wrapped-echo (wrap-auth-token identity))

(deftest wrap-auth-token-test
  (testing "token extracted from body"
    (swap! config assoc :token "abc12345")
    (let [req {:body         {:action "start"
                              :token  "abc12345"}
               :content-type "application/json"}
          resp (wrapped-echo req)]
      (is (not= 403 (:status resp)))))

  (testing "token extracted from querystring"
    (swap! config assoc :token "abc12345")
    (let [req {:params       {:token "abc12345"}
               :content-type "application/json"}
          resp (wrapped-echo req)]
      (is (not= 403 (:status resp)))))

  (testing "wrong token returns HTTP 403"
    (let [req {:params       {:token "wrong09876"}
               :content-type "application/json"}
          resp (wrapped-echo req)]
      (is (= 403 (:status resp))))))

