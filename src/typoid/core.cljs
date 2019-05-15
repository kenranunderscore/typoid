(ns typoid.core
  (:require [reacl2.core :as reacl :include-macros true]
            [reacl2.dom :as dom]
            [typoid.input :as input]
            [typoid.navbar :as navbar]))

(defn init! []
  (println "Initialization complete"))

(defn reload! []
  (println "Reloading finished"))

(defn handle-action [_ action]
  (cond
    (instance? input/StartTypingAction action)
    (reacl/return)

    (instance? input/FinishedTypingAction action)
    (reacl/return)))

(reacl/defclass main this []
  render
  (dom/div
   (navbar/navbar)
   (input/foo (reacl/opt :reduce-action handle-action)
              "This is an example text")))

(reacl/render-component
 (.getElementById js/document "react-root")
 main
 nil)
