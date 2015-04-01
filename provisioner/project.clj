(defproject bunsen/provisioner "0.1.0-SNAPSHOT"
  :description "Provisions things for Bunsen."
  :main bunsen.provisioner.service
  :test-paths ["src/test"]
  :source-paths ["src/main"]
  :dependencies [[org.clojure/clojure _]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/algo.generic "0.1.2"]
                 [com.stuartsierra/component _]
                 [ring _]
                 [ring/ring-json _]
                 [bidi _ :exclusions [org.clojure/clojure]]
                 [environ _]
                 [liberator _]
                 [wkf/clj-http _]
                 [pandect "0.5.1"]
                 [bunsen/common _]
                 [com.cemerick/url "0.1.1"]]
  :plugins [[lein-modules "0.3.10"]]
  :profiles {:uberjar {:aot [bunsen.provisioner.service]}
             :dev {:source-paths ["src/dev"]
                   :repl-options {:init (user/watch)
                                  :init-ns user}
                   :dependencies [[hawk "0.1.1"]
                                  [ring-mock "0.1.5"]
                                  [org.clojure/tools.namespace "0.2.10"]]}})
