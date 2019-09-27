(ns cljblog.config
    (:require [clojure.java.io :as io]
      [clojure.string :as str]
      [clojure.edn :as edn]
      [clj-yaml.core :as yaml]
      [clojure.data.json :as json]))

(defmulti read-config
          (fn [config]
              (cond (str/ends-with? config ".edn") :edn
                    (or (str/ends-with? config ".yaml")
                        (str/ends-with? config ".yml")) :yaml
                    (str/ends-with? config ".json") :json
                    :else :none)))

(defmethod read-config :yaml
           [config]
           (try
             (with-open [r (io/reader config)]
                        (yaml/parse-string (java.io.PushbackReader. r)))
             (catch java.io.IOException e
               (printf "Couldn't open '%s': %s\n" config (.getMessage e)))
             (catch RuntimeException e
               (printf "Error parsing yaml file '%s': %s\n" config (.getMessage e)))))

(defmethod read-config :json
           [config]
           (try
             (with-open [r (io/reader config)]
                        (json/read (java.io.PushbackReader. r)
                                   :key-fn keyword))
             (catch java.io.IOException e
               (printf "Couldn't open '%s': %s\n" config (.getMessage e)))
             (catch RuntimeException e
               (printf "Error parsing json file '%s': %s\n" config (.getMessage e)))))

(defmethod read-config :edn
           [config]
           (try
             (with-open [r (io/reader config)]
                        (edn/read (java.io.PushbackReader. r)))
             (catch java.io.IOException e
               (printf "Couldn't open '%s': %s\n" config (.getMessage e)))
             (catch RuntimeException e
               (printf "Error parsing edn file '%s': %s\n" config (.getMessage e)))))

;; Read from default location. Feel free to change this before
;; building and deploying.
(def conf-file (read-config "resources/config/cljblog.edn"))