(ns embodied.plants
  (:require [quil.core :as q]
            [quil.middleware :as m]))

; https://codepen.io/den1k/pen/vZZbmM?editors=0010

(defn iteration3 [{:as grammar :keys [rules start]}]
  (-> grammar
      (update :iteration inc)
      ;(assoc :angle (+ 40 (rand-int 80)))
      (update :start (fn [st]
                       (into [] (mapcat (fn [variable]
                                          (or
                                           (variable rules)
                                           [variable])))
                             st)))))


(defn point-offset [angle length]
  (let [rad (* angle (/ (* 2 Math/PI) 360))]
    [(* length (Math/cos rad))
     (* length (Math/sin rad))]))

(defn rf [{:keys [view-angle coords angle moves step-length] :as m} x]
  (let [point (peek coords)]
    (case (x moves)
      :forward (assoc m :coords
                        (conj coords (mapv + point (point-offset view-angle step-length))))
      :left (update m :view-angle - angle)
      :right (update m :view-angle + angle)
      :noop m)))

(defn grammar->coords [{:as grammar :keys [start coords step-length angle moves]}]
  (merge grammar
         (reduce rf
                 (merge grammar
                        {:coords     [[0 0]]
                         :view-angle 0})
                 start)))

(def grammar3
  {:start       [:A]
   :rules       {:A [:B :- :A :- :B]    ;; could quote these
                 :B [:A :+ :B :+ :A]}
   :angle       60
   :step-length 2
   :iteration   0
   :moves       {:A :forward
                 :B :forward
                 :- :left
                 :+ :right}})

(def iteration-with-coords3
  (comp
   grammar->coords
   iteration3))


(def sierpinski
  {:start       [:A]
   :rules       {:A [:B :- :A :- :B]    ;; could quote these
                 :B [:A :+ :B :+ :A]}
   :angle       60
   :step-length 3
   :iteration   0
   :moves       {:A :forward
                 :B :forward
                 :- :left
                 :+ :right}})

#_(->> (iterate iteration3 sierpinski)
     (take 10)
     (map :start)
     (map println))

(def dragon-curve
  {:start       [:F :X]
   :rules       {:X [:X :+ :Y :F]
                 :Y [:F :X :- :Y]}
   :angle       90
   :step-length 4
   :iteration   0
   :moves       {:F :forward
                 :X :noop
                 :Y :noop
                 :+ :left
                 :- :right}})

;(take 3 (iterate iteration-with-coords3 grammar4))

(defn setup []
  (q/frame-rate 1)
  ;(q/stroke 255)
  (q/color-mode :hsb 100)
  ;(q/stroke-weight 2)
  (q/background 0 0 0)
  ;sierpinski
  dragon-curve

  )

;; a nice shade of grey.
(defn draw [{:as grammar :keys [coords iteration]}]
  (q/color-mode :hsb 100)
  (q/stroke (rand-int 100) 100 100)
  ;(q/stroke-weight 2)
  ;(q/translate 0 400)                   ; sierpinsky
  (q/translate 400 400) ; dragoncurve
  (doseq [[p1 p2] (partition 2 1 coords)]
    (q/line p1 p2)))

(q/defsketch example
             :title "Make Plants w/ L-Systems"
             ;:settings #(q/smooth 2)
             :middleware [m/fun-mode]
             :setup setup
             :draw draw
             :mouse-pressed (fn [st _] (iteration-with-coords3 st))
             :size [800 800])

