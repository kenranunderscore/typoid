(ns typoid.core
  (:require
   [reacl2.core :as reacl :include-macros true]
   [reacl2.dom :as dom]))

(defn init! []
  (println "Initialization complete"))

(defn reload! []
  (println "Reloading finished"))

(defrecord KeyState [key-code key])

(defrecord CharacterState [state char])

(defn active-char [state]
  (:char
   (nth (:characters state)
        (:pos state))))

(defn advance [state]
  (let [pos (:pos state)]
    (-> state
        (assoc :pos (inc pos))
        (assoc-in [:characters pos :state] :correct))))

(defn done? [state]
  (>= (:pos state)
      (count (:characters state))))

(defn handle-key-event [key-state state]
  (reacl/return :local-state
                (if (and (not (done? state))
                         (= (:key key-state)
                            (active-char state)))
                  (advance state)
                  state)))

(reacl/defclass character this [character-state]
  render
  (dom/b {:style {:background-color (case (:state character-state)
                                      :untyped "yellow"
                                      :correct "green"
                                      :error "red"
                                      "white")}}
         (:char character-state)))

(defn allowed-key? [code]
  (let [characters (into #{}
                         (concat [8   ;; backspace
                                  13  ;; enter
                                  16  ;; shift
                                  17  ;; control
                                  32  ;; space
                                  160 ;; ^
                                  161 ;; !
                                  163 ;; #
                                  164 ;; $
                                  169 ;; ) (AZERTY)
                                  170 ;; *
                                  171 ;; ~
                                  173 ;; - (firefox)
                                  ]
                                 (into #{} (range 186 224)) ;; various punctuation characters
                                 (into #{} (range 48 91))   ;; alphanumerics
                                 ))]
    (contains? characters
               code)))

(defn make-foo-state [text]
  {:pos 0
   :characters (mapv (fn [c]
                       (->CharacterState :untyped c))
                     text)})

(reacl/defclass foo this [full-text]
  local-state [state (make-foo-state full-text)]

  render
  (dom/div {:style {:width "100%"
                    :height "100%"
                    :font-size "40px"
                    :font-family "monospace"}
            :tabIndex 0
            :onkeydown (fn [e]
                         (let [code (.-keyCode e)]
                           (when (allowed-key? code)
                             (.preventDefault e)
                             (reacl/send-message! this (->KeyState (.-keyCode e) (.-key e))))))}
           (map character (:characters state)))

  handle-message
  (fn [msg]
    (cond
      (instance? KeyState msg)
      (handle-key-event msg state))))

(reacl/render-component
 (.getElementById js/document "react-root")
 foo
 "Use some Uppercase letterS")
