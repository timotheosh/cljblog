(ns cljblog.web
  (:require [bidi.bidi :refer [match-route]]
            [bidi.ring :refer [make-handler files]]
            [bidi.vhosts :as vhosts]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5]]
            [ring.util.response :as res]))

(defn layout-page [request page]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0, shrink-to-fit=no"}]
    [:title "Tech blog"]
    [:link {:rel "stylesheet" :href "/styles/prism.css"}]]
   [:body
    [:script {:src "https://code.jquery.com/jquery-3.3.1.slim.min.js"}]
    [:script {:src "/js/prism.js" :language "javascript"}]
    [:script {:language "javascript"} "Prism.plugins.NormalizeWhitespace.setDefaults({
	'remove-trailing': true,
	'remove-indent': true,
	'left-trim': true,
	'right-trim': true,
	'break-lines': 100,
	/*'indent': 2,
	'remove-initial-line-feed': false,
	'tabs-to-spaces': 4,
	'spaces-to-tabs': 4*/
});"]
    [:div.logo "selfdidactic.com"]
    [:div.body page]]))

(defn index-handler
  [request]
  (res/response (layout-page request (html [:h1 "Default Page"]))))

(defn new-handler
  [request]
  (res/response (layout-page request (html [:h1 "test.selfdidactic.com"]))))

(def my-vhosts-model
  (vhosts/vhosts-model ["http://test.selfdidactic.com:8880"
                        ["/" [["" (files {:dir "/srv/websites/localtest/webroot/"})]]]]
                       [:*
                        ["/" index-handler]]))


(def my-routes [:host ":*"
                ["/" [
                      ["" index-handler]
                      ["index.html" index-handler]
                      ["public" [["" (files {:dir "/srv/websites/localtest/webroot/"})]]]]]])

(def handler
  (vhosts/make-handler my-vhosts-model))

(defn my-handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})
