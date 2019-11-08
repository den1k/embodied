(ns embodied.cinco-face-time-party-snoozer
  (:require [clojure.java.shell :refer [sh]]))

(defn say
  ([phrase] (sh "say" phrase))
  ([voice phrase]
   (sh "say" phrase "-v" voice)))

(comment
 (say "Samantha" "Hello friends")
 (say "Victoria" "Hello friends")
 (say "Alex" "Hello friends")
 (say "Fred" "Hello friends")
 (do
   (future (say "Samantha" "Hello friends"))
   (future (say "Fred" "Hello friends")))
 )

(def phrases
  ["Sounds good"
   "I understand"
   "Thanks for coming"
   "Okay"
   "Sure, why not"])

(def phrases-by-group
  ;; TODO come up with phrases together
  [])

(defn party-snoozer []
  (say "Victoria" (rand-nth phrases)))

(def t (Thread. #(while true
                   (party-snoozer)
                   (Thread/sleep 2000))))
(.start t)
(.stop t)
