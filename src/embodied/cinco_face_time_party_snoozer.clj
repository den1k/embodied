(ns embodied.cinco-face-time-party-snoozer
  (:require [clojure.java.shell :refer [sh]]))

(defn say
  ([phrase] (sh "say" phrase))
  ([voice phrase]
   (sh "say" phrase "-v" voice)))

(def phrases
  ["Once upon a time"
   "There was a mouse"
   "That loved cheese"
   "The mouse couldn't find cheese today"
   "So it needed to learn to make cheese"
   "It went to the library to look for books on how to make
   cheese"
   "It found and read 3 of them. The mouse learned that to make
   cheese it needs milk and salt."
   "Then the mouse went home to make cheese"])

(def voices
  ["Fred" "Alex" "Victoria" "Samantha"])

(map #(say (rand-nth voices) %) phrases)

(defn story [sentences])

(say "Victoria"
     "hey alice, how are you")



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
