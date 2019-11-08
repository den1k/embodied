(ns embodied.shuffle)

(def people
  ;; todo add names
  [])

(-> (shuffle people)
    ;; groups of 6
    (partition-all 6))

;; mhhh
(defn find-group-size [start-size ok-rest]
  (let [people-count 29                 ; fixme
        modolo       (mod people-count start-size)]
    (if-not (<= modolo ok-rest)
      (find-group-size (dec start-size) ok-rest)
      {:group-size       start-size
       :number-of-groups (int (/ people-count start-size))
       :rest             modolo}
      ))
  )

(find-group-size 4 3)
