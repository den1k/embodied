(ns embodied.functions)

(def people
  ;; TODO fill with names of people from group
  [{:name     "Alex"
    :dancing? true
    :tiptoes? false}
   {:name     "Eve"
    :dancing? false
    :tiptoes? false}
   {:name     "Jane"
    :dancing? true
    :tiptoes? false}
   {:name     "Fred"
    :dancing? false
    :tiptoes? false}])

;; *** map

(defn tiptoes! [person]
  (assoc person :tiptoes? true))

(tiptoes! (first people))

(map tiptoes! people)

;; *** filter

(filter :dancing? people)

;; *** group-by

(def dance-moves
  ;; TODO add to with group
  ["moon walk"
   "lock step"
   "spin"
   "stomp"
   "seagrass"
   "shuffle"])

(defn person-with-dance-move [person]
  (assoc person :dance-move (rand-nth dance-moves)))

(def people-with-dance-moves
  (map person-with-dance-move people))

(group-by :dance-move people-with-dance-moves)
