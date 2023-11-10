val b = Array.newBuilder[Int]

// assignment isn't happening here
b += 0

val b1 = b += 1
val b2 = b += 2
val b3 = b.addOne(5)

// All return the same result, as Array is mutable
b.result().foreach(println)
b1.result().foreach(println)
b2.result().foreach(println)
b3.result().foreach(println)