(ns embodied.people)

(def people
  ["Jane"
   "Eve"
   "Tom"
   "Brad"]
  )

(defn random-groups-of [group-size]
  (->> people
       shuffle
       (partition-all group-size)))

(random-groups-of 3)

(def group-namaes
  [:pina :chaplin :aphex])

(defn random-named-groups-of [group-size]
  (zipmap group-namaes (random-groups-of group-size)))

(random-named-groups-of 2)
