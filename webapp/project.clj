(defproject webapp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.4.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [com.h2database/h2 "1.4.190"]
                 [ring/ring-jetty-adapter "1.4.0"]]
  :main webapp.handler)


