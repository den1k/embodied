(ns embodied.plants
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;variables : A B
;constants : + −
;start     : A
;rules     : (A → B−A−B), (B → A+B+A)
;angle     : 60°


(def grammar
  {:variables #{:A :B}
   :constants #{:+ :-}
   :start     [:A]
   :rules     {:A [:B :- :A :- :B]      ;; could quote these
               :B [:A :+ :B :+ :A]}
   :angle     60
   :iteration 0
   :moves     {:A :forward
               :B :forward
               :- :left
               :+ :right}})

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

(pop [1 2 3])

;; Pythagorean theorem
;; a^2 + b^2 = c^2
; (Math/sin 60) = opposite-site-length / hypotenuse-site-length
(defn move [point angle distance]

  )
(defn grammar->coords [{:as grammar :keys [start turtle angle moves]}]
  (reduce (fn [{:keys [angle coords]} x]
            (let [point (peek coords)
                  next  (case (x moves)
                          :forward
                          :B 10
                          )])

            )
          {:coords [[0 0]]}
          start)

  (for [x start]

    )
  )

(comment
 (take 2 (iterate iteration grammar)))

(defn setup []
  (q/frame-rate 1)
  (q/stroke 255)
  ;(q/stroke-weight 0.1)
  #_(q/background 252 131 203)
  grammar)

;; a nice shade of grey.
(defn draw [{:as grammar}]
  (let [next-iter (iteration grammar)]
    (when-let [points (get lines current-line-idx)] ;; Set the y coord randomly within the sketch
      (q/scale 20)
      (doseq [[p1 p2] (partition 2 1 points)]
        (q/line p1 p2)))))

(q/defsketch example
             :title "Make Plants w/ L-Systems"
             ;:settings #(q/smooth 2)
             :middleware [m/fun-mode]
             :setup setup
             :draw draw
             :update (fn [state] (update state :current-line-idx inc))
             :size [400 400])

