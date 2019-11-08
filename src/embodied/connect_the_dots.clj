(ns embodied.connect-the-dots
  "Quil requires using JVM <= 8"
  (:require [quil.core :as q]
            [quil.middleware :as m]))

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
  [ [[1 6] [2 7] [3 7] [5 6] [6 4] [7 5] [6 6] [5 6]]])

(defn setup []
  (q/frame-rate 1)
  (q/stroke 255)
  (q/stroke-weight 0.1)
  (q/background 252 131 203)
  {:lines            kitty-cat-dots
                     #_ursa-major-flipped
   :current-line-idx 0})
;; a nice shade of grey.
(defn draw [{:keys [lines current-line-idx]}]
  (when-let [points (get lines current-line-idx)] ;; Set the y coord randomly within the sketch
    (q/scale 20)
    (doseq [[p1 p2] (partition 2 1 points)]
      (q/line p1 p2))))

(q/defsketch example
             :title "Connect The Dots!"
             ;:settings #(q/smooth 2)
             :middleware [m/fun-mode]
             :setup setup
             :draw draw
             :update (fn [state] (update state :current-line-idx inc))
             :size [400 400])
