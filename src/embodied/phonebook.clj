(ns embodied.phonebook)

(def people
  [{:name "Jane"
    :cell 1234567}
   {:name "Eve"
    :cell 3480958}
   {:name "Jim"
    :cell 2393920}])

(def phonebook
  (into
   {}
   (map (fn [person]
          [(:name person) person]))
   people)
  )

(defn lookup [name]
  (get phonebook name))

(comment
 phonebook
 (lookup "Eve")
 )
