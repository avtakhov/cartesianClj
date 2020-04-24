(ns test
  (:require [main :as tree]))
(def size 10000)
(def a
  (loop [i 0
         a nil]

    (if (< i size) (recur (inc i) (tree/insert a i)) a)))

(println "inserted" size "items")

(assert (= (range size) (tree/lst a)))

(def res (tree/split a 10))

(assert (tree/contains (first res) 10))

(assert (= (range 11) (tree/lst (first res))))

(def a (tree/mergeTrees (first res) (second res)))

(assert (= (range size) (tree/lst a)))

(assert (> 1000(tree/depth a)))

(println "depth" (tree/depth a))