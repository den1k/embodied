(ns embodied.dance)

(def all-steps
  [:forward :left :right :360-spin :180-spin :jump :back :hands-up])


(defn random-steps [step-count]
  (take step-count (repeatedly #(rand-nth all-steps))))

(comment
 (random-steps 10)
 )
