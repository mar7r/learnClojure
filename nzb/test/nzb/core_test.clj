(ns nzb.core-test
  (:use clojure.test
        nzb.core)
  (:require [clojure.java.io :as io]))

(deftest test-nzb->map
  (let [input (io/resource "example.nzb")]
    (is (= {:meta {:title "Your File!"
                  :tag "Example"}
           :files [{:poster "Joe Bloggs <bloggs@nowhere.example>"
                    :date 1071674882
                    :subject "Here's your file! abc-mr2a.r01 (1/2)"
                    :groups ["alt.binaries.newzbin"
                             "alt.binaries.mojo"]
                    :segments [{:bytes 102394
                                :number 1
                                :id "123456789abcdef@news.newzbin.com"}
                               {:bytes 4501
                                :number 2
                                :id "987654321fedbca@news.newzbin.com"}]}]}
       (nzb->map input)))))
