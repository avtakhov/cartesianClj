(ns main)

(defn node [x]
  {:x     x
   :y     (rand)
   :left  nil
   :right nil})

(defn copy [t l r]
  (assoc t :left l :right r))

(defn value [name] (fn [t] (get t name)))

(def getX (value :x))
(def left (value :left))
(def right (value :right))
(def getY (value :y))

(defn split [t k]
  (cond
    (nil? t) (vector nil nil)
    (> (getX t) k) (let [res (split (left t) k)
                         t' (copy t (second res) (right t))
                         ans (vector (first res) t')]
                     ans)
    :else (let [res (split (right t) k)
                t' (copy t (left t) (first res))
                ans (vector t' (second res))]
            ans)))

(defn mergeTrees [t1 t2]
  (cond
    (nil? t1) t2
    (nil? t2) t1
    (> (getY t1) (getY t2)) (let [t2l (mergeTrees t1 (left t2))
                                  ans (copy t2 t2l (right t2))]
                              ans)
    :else (let [t1r (mergeTrees (right t1) t2)
                ans (copy t1 (left t1) t1r)]
            ans)))

(defn trace [x] (do (println x) x))

(defn lst [t]
  (if (nil? t)
    (list)
    (concat
      (lst (left t)) (conj (lst (right t)) (getX t)))))

(defn out [t]
  (if (some? t) (do (out (left t)) (println (getX t)) (out (right t))) nil))

(defn depth [t]
  (if (some? t) (inc (java.lang.Math/max (depth (left t)) (depth (right t)))) 0))

(defn insert [t x]
  (let [res (split t x)
        scnd (mergeTrees (node x) (second res))
        ans (mergeTrees (first res) scnd)]
    ans))

(defn contains [t x]
  (cond
    (nil? t) false
    (= (getX t) x) true
    (> (getX t) x) (contains (left t) x)
    :else (contains (right t) x)))