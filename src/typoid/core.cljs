(ns typoid.core
  (:require
   [reacl2.core :as reacl :include-macros true]
   [reacl2.dom :as dom]
   [typoid.input :as input]))

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

(reacl/render-component
 (.getElementById js/document "react-root")
 input/foo
 (reacl/opt :reduce-action handle-action)
 "Use some Uppercase letterS")
