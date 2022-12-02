;; # 🎄 Advent of Clerk: Day 1
(ns advent-of-clerk.day-01
  (:require [nextjournal.clerk :as clerk])
  (:require [clojure.string :as str]))
(clerk/serve! {:watch-paths ["."] :browse? true})
(clerk/show! "src/advent_of_clerk/day_01.clj")
(+ 1 1)

(def data
  (->> "src/advent_of_clerk/day_01-input.txt"
    (slurp)
    (#(str/split % #"\n\n"))
    (map #(->> %
               (str/split-lines)
               (map parse-long)))))

;; # Part 1
(->> data
     (map #(reduce + %))
     (reduce max))
;; # Part 2
(->> data
     (map #(reduce + %))
     (sort-by -)
     (take 3)
     (reduce +))
