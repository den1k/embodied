(ns embodied.functions
  (:require [clojure.java.shell :refer [sh]]
            [clojure.repl :refer [source]]))

(comment
 +

 (+ 2 3 4)

 (source +))

;; Let's make a greeting function

(comment
 (defn )

 (defn greet)

 (defn greet [name])

 (defn greet [name]
   "Hi")

 (defn greet [name]
   "Hi" name)

 ;; but that doesn't work
 ;; it just returns the last value
 ;; we need sth to combine the two
 ;; `str` to the rescue

 (defn greet [name]
   (str "Hi" name))

 )

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

(defn greet [name]
  (say "Victoria" (str "Hello " name)))



;; *** definition

(defn function-1 []
  ;; something it does
  (println "I'm function-1")
  )

(defn function-2 [param-1]
  (println "I'm function-2 with param-1: " param-1)
  )

(defn function-3 [param-1 param-2]
  (println "I'm function-3 with param-1: "
           param-1
           "and param-2: "
           param-2)
  )

;; *** invocation

(comment
  (function-1)
  (function-2 "Hello")
  (function-3 "Hello" "Is it me you're looking for")

  )
