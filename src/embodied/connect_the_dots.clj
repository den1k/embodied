(ns embodied.connect-the-dots
  "Quil requires using JVM <= 8"
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def cat-by-chloe
  [;; left whiskers
   [[2 6] [3 7] [2 7]]
   [[3 7] [2 8]]
   ;; right whiskers
   [[11 6] [10 7] [11 7]]
   [[10 7] [11 8]]
   ;; mouth
   [[5 9] [8 9] [8 10] [7 11] [6 11] [5 10] [7 10]]
   ;; nose
   [[6 6] [5 8] [6 8] [6 7] [7 7] [7 8] [8 8] [7 6]]
   ;; left eye
   [[3 4] [5 4] [5 5] [3 5] [3 4]]
   [[3 3] [5 3] [6 4] [6 5] [5 6] [3 6] [2 5] [2 4] [3 3]]
   ;; right eye
   [[8 4] [10 4] [10 5] [8 5] [8 4]]
   [[8 3] [10 3] [11 4] [11 5] [10 6] [8 6] [7 5] [7 4] [8 3]]
   ;; face contour
   [[3 1]
    [4 1]
    [5 2]
    [8 2]
    [9 1]
    [10 1]
    [12 4]
    [12 8]
    [9 12]
    [4 12]
    [1 8]
    [1 4]
    [3 1]]])

(def cat-by-chloe2
  [
   [[2 6] [3 7] [2 7]]                  ; micah
   [[3 7] [2 8]]                        ; scarlet
   [[11 6] [10 7] [11 7]]               ; austin
   [[10 7] [11 8]]                      ; sascha

   [[5 9] [8 9] [8 10] [7 11] [6 11] [5 10] [7 10]] ; alison

   [[6 6] [5 8] [6 8] [6 7] [7 7] [7 8] [8 8] [7 6]] ; chloe

   [[3 4] [5 4] [5 5] [3 5] [3 4]]      ; yedi
   [[3 3] [5 3] [6 4] [6 5] [5 6] [3 6] [2 5] [2 4] [3 3]] ; abby

   [[8 4] [10 4] [10 5] [8 5] [8 4]]    ; tara
   [[8 3] [10 3] [11 4] [11 5] [10 6] [8 6] [7 5] [7 4] [8 3]] ; walter

   [[3 1]
    [4 1]
    [5 2]
    [8 2]
    [9 1]
    [10 1]
    [12 4]
    [12 8]
    [9 12]
    [4 12]
    [1 8]
    [1 4]
    [3 1]]                              ; rachel
   ])

(defn grid-lines [cell-size grid-size]
  (let [points (range 0 (inc grid-size) cell-size)]
    (concat
     (map (fn [x] [[x 0] [x grid-size]]) points)
     (map (fn [y] [[0 y] [grid-size y]]) points))))

(def grid-size 13)
(def scale 60)
(def dims (repeat 2 (* scale grid-size)))

(defn setup []
  ;(q/frame-rate 1)
  (q/color-mode :hsb 100)
  (q/background 4 10 100)
  (q/scale scale)
  (q/stroke-weight 0.02)
  ;(q/stroke 255 100)
  (q/stroke 0 30)
  (doseq [[p1 p2] (grid-lines 1 grid-size)]
    (q/line p1 p2))
  (q/stroke 255)
  (q/stroke-weight 0.1)
  {:lines face-by-chloe})
;; a nice shade of grey.
(defn draw [{:keys [current-lines]}]
  (when-let [[[p1 p2]] current-lines]
    (println p1 p2)
    (q/scale scale)
    ;(q/color-mode :hsb 100)
    ;(q/stroke (rand-int 100) 100 90)
    (q/line p1 p2)))

(defn jitter [[[x1 y1] [x2 y2]]]
  (let [dx        (- x2 x1)
        dy        (- y2 y1)
        step      0.1
        wiggle    0.4
        wiggle-fn #(- (* wiggle (rand)) (/ wiggle 2))]
    (mapv (fn [fac]
            [(+ x1 (* dx fac) (wiggle-fn))
             (+ y1 (* dy fac) (wiggle-fn))])
          (range 0 (+ 1 step) step))))


(comment

 (->> face-by-chloe
      first
      (partition 2 1)
      (mapcat (comp #(partition 2 1 %) jitter))

      )
 )

(q/defsketch
 example
 :title "Connect The Dots!"
 ;:settings #(q/smooth 2)
 :middleware [m/fun-mode]
 :setup setup
 :draw draw
 :update (fn [{:as state :keys [lines current-lines]}]
           (if-let [more-points (next current-lines)]
             (assoc state
               :current-lines more-points)
             (do
               (q/color-mode :hsb 100)
               (q/stroke (rand-int 100) 100 90)
               (assoc state
                 :lines (next lines)
                 :current-lines (->> lines
                                     first
                                     (partition 2 1)
                                     (mapcat (comp #(partition 2 1 %) jitter))
                                     not-empty)))))
 :size dims
 :features [:keep-on-top]
 )
