(ns advent-of-clerk.interval-trees)


(defn make-interval-tree
  "https://en.wikipedia.org/wiki/Interval_tree#Augmented_tree"
  [v]
  (case (count v)
    0 nil
    1 {:x (-> v (get 0) (get 0)) :y (-> v (get 0) (get 1))
       :max-y-in-subtree (-> v (get 0) (get 1))}
    ;; else
    (let [middle (/ (unchecked-divide-int (count v) 2))
          interval (get v middle)
          l (make-interval-tree (subvec v 0 middle))
          r (make-interval-tree (subvec v (+ middle 1)))]
      {:x (get interval 0) :y (get interval 1)
       :max-y-in-subtree (apply max (->> [l r]
                                         (map :max-y-in-subtree)
                                         (filter #(do %))))
       :left l :right r})))


(make-interval-tree [[1 2] [2 4] [5 8]])

(defn contained-in-tree-or-in-it-more-than-once?
  ([interval tree]
   (contained-in-tree-or-in-it-more-than-once? interval tree nil))
  ([[x y] tree found-once?]
   (when tree
     (cond
       (and (< x (:x tree))
            (>= (:may-y-in-subtree tree) y))
       (recur [x y] (:left tree) found-once?)

       (or
        (and (> x (:x tree))
             (>= (:y tree) y))
        (and (>= x (:x tree))
             (> (:y tree) y)))
       true ; found a containing interval

       (and (> x (:x tree))
            (>= (:may-y-in-subtree tree) y))
       (recur [x y] (:right tree) found-once?)

       (and (= x (:x tree))
            (= y (:y tree)))
       (if found-once?
         true ; this is the second time
         (or (contained-in-tree-or-in-it-more-than-once? [x y] (:left tree) true)
             (contained-in-tree-or-in-it-more-than-once? [x y] (:right tree) true)))))))


(contained-in-tree-or-in-it-more-than-once?
 [1 3]
 (make-interval-tree [[1 2] [1 3] [1 3]]))
