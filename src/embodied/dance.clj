(ns embodied.dance)

(def initial-steps
  [:forward :left :forward :right :spin :jump :back])

(defn recursive-choreography [steps]
  (when (not-empty steps)
   (println steps)
   (recursive-choreography (pop steps))))

(comment

 (recursive-choreography initial-steps)

 )

(def initial-steps-list
  (apply list initial-steps))

(comment

 (recursive-choreography initial-steps-list)
 )

(defn recursive-choreography-2 [num steps]
  (when-not (= num (inc (count steps)))
    (println (take num steps))
    (recursive-choreography-2 (inc num) steps))
  )


(comment

 (recursive-choreography-2 1 initial-steps)
 )

(def all-steps
  [:forward :left :right :360-spin :180-spin :jump :back :hands-up])

(defn random-steps [step-count]
  (take step-count (repeatedly #(rand-nth all-steps))))

(comment
 (random-steps 10)
 )

(defn random-steps-infinite []
   (repeatedly #(rand-nth all-steps)))

(comment
 (take 10 (random-steps-infinite))
 )
