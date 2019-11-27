(ns embodied.recursion)

(defn repeat-phrase
  ([n letter] (repeat-phrase n "" letter))
  ([n text letter]
   (if (zero? n)
     text
     (repeat-phrase (dec n) (str text letter) letter))))

(comment

 (zero? 0)
 ; => true

 (dec 5)
 ; => 4

 (str "this" "and" "that")
 ; => "thisandthat"

 (repeat-phrase 5 "Oo")
 ; => "OoOoOoOoOo"
 (repeat-phrase 5 "😂️")
 ; => "😂️😂️😂️😂️😂️"
 )
