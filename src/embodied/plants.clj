(ns embodied.plants
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;variables : A B
;constants : + −
;start     : A
;rules     : (A → B−A−B), (B → A+B+A)
;angle     : 60°


(def grammar
  {:variables   #{:A :B}
   :constants   #{:+ :-}
   :start       [[:A]]
   :rules       {:A [:B :- :A :- :B]    ;; could quote these
                 :B [:A :+ :B :+ :A]}
   :angle       20
   :step-length 2
   :iteration   0
   :moves       {:A :forward
                 :B :forward
                 :- :left
                 :+ :right}})

(defn iteration2 [{:as grammar :keys [rules start]}]
  (-> grammar
      (update :iteration inc)
      (update :start (fn [st]
                       (conj st (into [] (mapcat (fn [variable]
                                                   (or
                                                    (variable rules)
                                                    [variable])))
                                      (peek st)))))))

(defn iteration3 [{:as grammar :keys [rules start]}]
  (-> grammar
      (update :iteration inc)
      (assoc :angle (+ 40 (rand-int 80)))
      (update :start (fn [st]
                       (into [] (mapcat (fn [variable]
                                          (or
                                           (variable rules)
                                           [variable])))
                             st)))))

(defrecord Node [branches])
(defrecord Branch [nodes])



(take 3 (iterate iteration2 grammar))

(defn iteration [{:as grammar :keys [rules start]}]
  (-> grammar
      (update :iteration inc)
      (update :start (fn [st]
                       (into []
                             (mapcat (fn [variable]
                                       (or
                                        (variable rules)
                                        [variable])))
                             st)))))

;(take 3 (iterate iteration (assoc grammar :start [:A])))

; https://codepen.io/den1k/pen/vZZbmM?editors=0010

;; Pythagorean theorem
;; a^2 + b^2 = c^2
; (Math/sin angle) = opposite-site-length / hypotenuse-site-length
; (Math/sin angle) * hypotenuse-site-length = opposite-site-length
; a/sing = hypo / oppo

; 1 / sin * oppo =
; (Math/sin 0) = a / c
; (Math/cos 0) = b / c
; (Math/tan 0) = a / b
(* 5 (Math/sin 60))                     ; length of a
(* 5 (Math/cos 90))



;; lengh of c
;; angle of C
;; solve for b, a

;Calculates 2 sides based on 3 given angles and 1 side.
;b = a·sin(B)/sin(A) = 7.07107 = 5√2
;c = a·sin(C)/sin(A) = 7.07107 = 5√2


(defn point-offset [angle length]
  (let [rad (* angle (/ (* 2 Math/PI) 360))]
    [(* length (Math/cos rad))
     (* length (Math/sin rad))]))

(point-offset 90 5)

(defn move [point angle distance]

  )

(defn grammar->coords [{:as grammar :keys [start coords step-length angle moves]}]
  (merge grammar
         (reduce (fn [{:keys [view-angle coords] :as m} x]
                   (let [point (peek coords)]
                     (case (x moves)
                       :forward (assoc m :coords
                                         (conj coords (mapv + point (point-offset view-angle step-length))))
                       :left (update m :view-angle - angle)
                       :right (update m :view-angle + angle)
                       )))
                 {:coords     [[0 0]]
                  :view-angle 0}
                 start)))

(-> (take 3 (iterate iteration2 grammar))
    last
    (update :start peek)
    grammar->coords)

(def grammar3
  {:start       [:A]
   :rules       {:A [:B :- :A :- :B]    ;; could quote these
                 :B [:A :+ :B :+ :A]}
   :angle       20
   :step-length 2
   :iteration   0
   :moves       {:A :forward
                 :B :forward
                 :- :left
                 :+ :right}})

(->> (take 3 (iterate (comp grammar->coords iteration3) grammar3))
     last
     :start
     (map name)
     (clojure.string/join))

(defn iteration-with-coords [grammar]
  (as-> (iteration2 grammar) grmr
        (assoc grmr :coords (mapcat grammar->coords grmr))))
(->
 (take 4 (iterate (comp
                   grammar->coords
                   iteration3
                   ) grammar3))
 last
 :coords)

(def iteration-with-coords3 (comp
                             grammar->coords
                             iteration3))


(def grammar4
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

(take 3 (iterate iteration-with-coords3 grammar4))

(defn setup []
  (q/frame-rate 1)
  (q/stroke 255)
  (q/color-mode :hsb 100)
  ;(q/stroke-weight 2)
  ;(q/background 252 131 203)
  (q/background 0)
  grammar4

  )

;; a nice shade of grey.
(defn draw [{:as grammar :keys [coords iteration]}]
  (q/color-mode :hsb)
  (q/stroke (rand-int 100) 100 100)
  (q/translate 0 400)
  (doseq [[p1 p2] (partition 2 1 coords)]
    (q/line p1 p2)))

(q/defsketch example
             :title "Make Plants w/ L-Systems"
             ;:settings #(q/smooth 2)
             :middleware [m/fun-mode]
             :setup setup
             :draw draw
             :mouse-pressed (fn [st _] (iteration-with-coords3 st))
             #_:update #_(fn [_] {:coords [[(rand-int 100) (rand-int 100)]
                                         [(rand-int 100) (rand-int 100)]]})
             ;iteration-with-coords3
             :size [800 800])

