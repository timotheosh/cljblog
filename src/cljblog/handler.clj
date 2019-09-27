(ns cljblog.handler
  (:require [cljblog.config :refer [conf-file]]
            [cljblog.web :as web]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.flash :refer [wrap-flash]]))

(def dev-app
  (wrap-session web/handler))
