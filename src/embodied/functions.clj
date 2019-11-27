(ns embodied.functions)

(def people
  [{:name "Rachel", :dancing? false, :tiptoes? false}
   {:name "Austin", :dancing? false, :tiptoes? false}
   {:name "Macie", :dancing? false, :tiptoes? false}
   {:name "Alice A", :dancing? false, :tiptoes? false}
   {:name "Micah", :dancing? false, :tiptoes? false}
   {:name "Chloe", :dancing? false, :tiptoes? false}
   {:name "Scarlet", :dancing? false, :tiptoes? false}
   {:name "Sascha", :dancing? false, :tiptoes? false}
   {:name "Walter", :dancing? false, :tiptoes? false}])


;; *** map


(defn tiptoes! [person]
  (println "I'm putting" (:name person) "on tiptoes")
  (Thread/sleep 1000)
  (assoc person :tiptoes? true))

(tiptoes! (first people))

(map tiptoes! people)

;; *** filter

(filter :dancing? people)

;; *** group-by

(def dance-moves
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
