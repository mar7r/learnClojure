(ns nzb.core
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.zip :as zip]
            [clojure.data.zip.xml :as zip-xml])
  (:import (javax.xml.parsers SAXParserFactory SAXParser)))

(defn meta->map
  [root]
  (into {}
        (for [m (zip-xml/xml-> root :head :meta)]
          [(keyword (zip-xml/attr m :type))
           (zip-xml/text m)])))

(defn segment->map
  [seg]
  {:bytes (Long/valueOf (zip-xml/attr seg :bytes))
   :number (Integer/valueOf (zip-xml/attr seg :number))
   :id (zip-xml/xml1-> seg zip-xml/text)})

(defn file-map
  [file]
  {:poster (zip-xml/attr file :poster)
   :date (Long/valueOf (zip-xml/attr file :date))
   :subject (zip-xml/attr file :subject)
   :groups (vec (zip-xml/xml-> file :groups :group zip-xml/text))
   :segments (mapv segment->map
                   (zip-xml/xml-> file :segments :segment))}
  )

(defn startparse-sax
  "Don't validate the DTDs, they are usually messed up"
  [s ch]
  (let [factory (SAXParserFactory/newInstance)]
    (.setFeature factory "http://apache.org/xml/features/nonvalidating/load-external-dtd" false)
    (let [^SAXParser parser (.newSAXParser factory)]
      (.parse parser s ch))))

(defn attr-fn
  [attrname f test-val & [conv-fn]]
  (fn [loc]
    (let [conv-fn (or conv-fn identity)
          val (conv-fn (zip-xml/attr loc attrname))]
      (f val test-val))))

(defn nzb->map
  [input]
  (let [root (-> input
                 io/input-stream
                 (xml/parse startparse-sax)
                 zip/xml-zip)]
    {:meta (meta->map root)
     :files (mapv file-map (zip-xml/xml-> root :file))}))

