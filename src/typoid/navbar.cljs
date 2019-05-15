(ns typoid.navbar
  (:require [reacl2.core :as reacl :include-macros true]
            [reacl2.dom :as dom]))

(reacl/defclass navbar this []
  render
  (dom/nav {:class "navbar navbar-default navbar-expand navbar-dark bg-dark"}
           (dom/a {:class "navbar-brand"
                   :href "#"}
                  "TYPOID")
           (dom/button {:class "navbar-toggler"
                        :type "button"
                        :data-toggle "collapse"
                        :data-target "#navbarContent"}
                       (dom/span {:class "navbar-toggler-icon"}))
           (dom/div {:class "collapse navbar-collapse"
                     :id "navbarContent"}
                    (dom/ul {:class "navbar-nav mr-auto"}
                            (dom/li {:class "nav-item"}
                                    (dom/a {:class "nav-link"
                                            :href "#"}
                                           "Home"))
                            (dom/li {:class "nav-item"}
                                    (dom/a {:class "nav-link"
                                            :href "#"}
                                           "Something"))
                            (dom/li {:class "nav-item"}
                                    (dom/a {:class "nav-link"
                                            :href "#"}
                                           "Foo Bar"))))))
