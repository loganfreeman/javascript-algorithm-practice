(ns clojureproject.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(println (foo "Mike"))


; This is Clojure code.
; When a set is used as a function, it returns the argument if it is
; in the set and nil otherwise.  When used in a boolean context,
; that indicates whether the argument is in the set.
(def vowel? (set "aeiou"))

(defn pig-latin [word] ; defines a function
  ; word is expected to be a string
  ; which can be treated like a sequence of characters.
  (let [first-letter (first word)] ; assigns a local binding
    (if (vowel? first-letter)
      (str word "ay") ; then part of if
      (str (subs word 1) first-letter "ay")))) ; else part of if

(println (pig-latin "red"))
(println (pig-latin "orange"))

(def ^:dynamic v 1) ; v is a global binding

(defn f1 []
  (println "f1: v:" v)) 

(defn f2 []
  (println "f2: before let v:" v) 
  ; creates local binding v that shadows global one
  (let [v 2]
    ; local binding only within this let statement
    (println "f2: in let, v:" v)
    (f1))
  ; outside of this let, v refers to global binding
  (println "f2: after let v:" v))

(defn f3 []
  (println "f3: before binding v:" v) 
  ; same global binding with new, temporary value
  (binding [v 3] 
    ; global binding, new value
    (println "f3: within binding function v: " v) 
    (f1)) ; calling f1 with new value to v
  ; outside of binding v refers to first global value
  (println "f3: after binding v:" v)) 

(defn f4 []
 (def v 4)) ; changes the value of v in the global scope

(println "(= v 1) => " (= v 1))
(println "Calling f2: ")
(f2)
(println)
(println "Calling f3: ")
(f3)
(println)
(println "Calling f4: ")
(f4)
(println "after calling f4, v =" v)