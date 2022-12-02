;; # 🎄 Advent of Clerk: Day 2
(ns advent-of-clerk.day-02
  (:require [clojure.string :as str]))

(defn game-score-part-1 [game]
  (let [[opponent me] game]
    (+
     (or (get {"X" 1
               "Y" 2
               "Z" 3} me)
         (throw (new Exception (str "me is " me))))
     ;; winner bonus
     (get { ;; draws
           ["A" "X"] 3
           ["B" "Y"] 3
           ["C" "Z"] 3
           ;; wins
           ["A" "Y"] 6
           ["B" "Z"] 6
           ["C" "X"] 6}
          ;; loss is default value
          [opponent me] 0))))

(get { ;; draws
           ["A" "X"] 3
           ["B" "Y"] 3
           ["C" "Z"] 3
           ;; wins
           ["A" "Y"] 6
           ["B" "Z"] 6
           ["C" "X"] 6}
          ;; loss is default value
          ["A" "x"] 0)

(def data (->> "src/advent_of_clerk/day_02-input.txt"
               (slurp)
               (#(str/split % #"\n"))
               (map #(str/split % #" "))))



;; part 1
(->> data
     (map game-score-part-1)
     (reduce +))

;; part 2
(defn game-score-part-2 [game]
  (let [[opponent wanted-result] game
        me (get { ;; draws
                 ["A" "Y"] "X"
                 ["B" "Y"] "Y"
                 ["C" "Y"] "Z"
                 ;; wins
                 ["A" "Z"] "Y"
                 ["B" "Z"] "Z"
                 ["C" "Z"] "X"
                 ;; losses
                 ["A" "X"] "Z"
                 ["B" "X"] "X"
                 ["C" "X"] "Y"}
                [opponent wanted-result])]
    (game-score-part-1 [opponent me])))

(->> data
     (map game-score-part-2)
     (reduce +))
