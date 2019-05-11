(ns typoid.core
  (:require
   [reacl2.core :as reacl :include-macros true]
   [reacl2.dom :as dom]))

(defn init! []
  (println "Initialization complete"))

(defn reload! []
  (println "Reloading finished"))

(defrecord KeyState [key-code key])

(defn handle-key-event [key-state pos text]
  (let [adjust (if (= (:key key-state)
                      (nth text pos))
                 inc
                 identity)]
    (reacl/return :local-state (adjust pos))))

(reacl/defclass letter this state [letter]
  render
  (dom/b {:style {:background-color (case state
                                      :new "yellow"
                                      :correct "green"
                                      :error "red"
                                      "white")}}
         letter))

(reacl/defclass foo this [full-text]
  local-state [pos 0]

  render
  (dom/div {:style {:width "100%"
                    :height "100%"
                    :font-size "40px"
                    :font-family "monospace"}
            :tabIndex 0
            :onkeydown (fn [e]
                         (.preventDefault e)
                         (reacl/send-message! this (->KeyState (.-keyCode e) (.-key e))))}
           (map-indexed (fn [i x] (letter (if (< i pos) :correct :new) x))
                        full-text))

  handle-message
  (fn [msg]
    (cond
      (instance? KeyState msg)
      (handle-key-event msg pos full-text))))

(reacl/render-component
 (.getElementById js/document "react-root")
 foo
 "these are some random words for testing")
