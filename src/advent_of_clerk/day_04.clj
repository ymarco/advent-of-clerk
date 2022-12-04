;; # 🎄 Advent of Clerk: Day 4
(ns advent-of-clerk.day-01
  (:require [clojure.string :as str]))

(defn parse-interval [s]
  (->> s
       (#(str/split % #"-"))
       (map parse-long)
       (into [])))

(defn parse-line [s]
  (->> s
       (#(str/split % #","))
       (map parse-interval)))

(def data
  (->> "src/advent_of_clerk/day_04-input.txt"
    (slurp)
    (str/split-lines)
    (map parse-line)))

;; part1
(defn one-contains-another? [[a b] [c d]]
  (or (<= a c d b)
      (<= c a b d)))

(->> data
     (map #(apply one-contains-another? %))
     (map #(if % 1 0))
     (reduce +))

;; part2
(defn overlap? [[a b] [c d]]
  (or (<= a c b)
      (<= c a d)))

(->> data
     (map #(apply overlap? %))
     (map #(if % 1 0))
     (reduce +))
