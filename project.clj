(defproject cljblog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-yaml "0.4.0"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [bidi "2.1.6"]
                 [hiccup "1.0.5"]
                 [markdown-clj "1.10.0"]
                 [enlive "1.1.6"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:port 8880
         :handler cljblog.handler/dev-app
         :auto-refresh? true
         :auto-reload? true}
  :main ^:skip-aot cljblog.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repl-options {:init-ns cljblog.core})
