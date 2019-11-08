(ns embodied.a-cappella
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

(say "Samantha" "hello jackie")
;; have the computer sing it uglyly

(defn greet [])

