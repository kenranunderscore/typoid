(ns typoid.core
  (:require
   [reacl2.core :as reacl :include-macros true]
   [reacl2.dom :as dom]))

(reacl/defclass foo []
  render
  (dom/div "hiiiiii"))

(defn init []
  (reacl/render-component
   (.getElementById js/document "react-root")
   foo))
