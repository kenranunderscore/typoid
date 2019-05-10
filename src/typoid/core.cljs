(ns typoid.core
  (:require
   [reacl2.core :as reacl :include-macros true]
   [reacl2.dom :as dom]))

(reacl/defclass foo []
  render
  (dom/div "o21"))

(defn init! []
  (println "Initialization complete"))

(defn reload! []
  (println "Reloading finished"))

(reacl/render-component
 (.getElementById js/document "react-root")
 foo)
