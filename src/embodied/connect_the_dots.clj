(ns embodied.connect-the-dots
  "Quil requires using JVM <= 8"
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def face-by-chloe
  [;; left dimple
   [[2 6] [3 7] [2 7]]
   [[3 7] [2 8]]
   ;; right dimple
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
;; todo draw grid

(defn grid-lines [cell-size grid-size]
  (let [points (range 0 (inc grid-size) cell-size)]
    (concat
     (map (fn [x] [[x 0] [x grid-size]]) points)
     (map (fn [y] [[0 y] [grid-size y]]) points))
    )
  )
;(grid-lines 2 10)

(clojure.walk/postwalk #(if (and (vector? %) (number? (first %)))
                          (-> %
                              (update 0 + 1)
                              (update 1 + 1)
                              )
                          %)
                       face-by-chloe)

(def kitty-cat-dots
  "https://www.worksheetworks.com/math/geometry/graphing/coordinate-pictures/cat.html"
  [[[4 8] [7 7] [4 6]]
   [[14 15] [13 16] [12 15] [12 12]]
   [[6 18] [5 19]]
   [[14 7] [11 7]]
   [[10 18] [8 18] [7 17] [6 15]]
   [[14 16]
    [15 17]
    [17 17]
    [19 16]
    [20 13]
    [20 11]
    [19 9]
    [17 8]
    [15 8]
    [14 9]
    [16 9]
    [17 9]
    [18 10]
    [19 12]
    [18 15]
    [17 16]
    [16 16]
    [14 14]]
   [[14 8] [11 7] [14 6]]
   [[8 7] [9 8] [10 7]]
   [[8 18]
    [6 19]
    [4 19]
    [4 18]
    [6 17]
    [5 15]
    [6 13]
    [5 11]
    [3 10]
    [1 7]
    [2 6]
    [3 7]
    [4 9]
    [6 10]
    [7 9]
    [6 7]
    [6 2]
    [8 4]
    [10 4]
    [12 2]
    [12 7]
    [11 9]
    [13 10]
    [14 11]
    [14 16]
    [12 19]
    [10 19]
    [10 18]
    [12 17]
    [10 16]
    [9 15]
    [9 14]
    [10 13]]
   [[11 19] [12 18]]
   [[4 7] [7 7]]
   [[5 18] [4 19]]
   [[11 18] [10 19]]])

(def ursa-major
  [[[1 2] [2 1] [3 1] [5 2] [6 4] [7 3] [6 2] [5 2]]])

(def ursa-major-flipped
  [[[1 6] [2 7] [3 7] [5 6] [6 4] [7 5] [6 6] [5 6]]])

(def grid-size 13)
(def scale 30)
(def dims (repeat 2 (* scale grid-size)))

(defn setup []
  ;(q/frame-rate 1)
  (q/background 252 131 203)
  (q/scale 30)
  (q/stroke-weight 0.05)
  (q/stroke 255 100)
  (doseq [[p1 p2] (grid-lines 1 grid-size)]
    (q/line p1 p2))
  (q/stroke 255)
  (q/stroke-weight 0.1)

  {:lines            #_kitty-cat-dots face-by-chloe
   #_ursa-major-flipped
   :current-line-idx                  0})
;; a nice shade of grey.
(defn draw [{:keys [lines current-line-idx]}]
  (when-let [points (get lines current-line-idx)] ;; Set the y coord randomly within the sketch
    (q/scale 30)
    (doseq [[p1 p2] (partition 2 1 points)]
      (q/line p1 p2))))

(q/defsketch example
             :title "Connect The Dots!"
             ;:settings #(q/smooth 2)
             :middleware [m/fun-mode]
             :setup setup
             :draw draw
             :update (fn [state] (update state :current-line-idx inc))
             :size dims
             :features [:keep-on-top]
             )
