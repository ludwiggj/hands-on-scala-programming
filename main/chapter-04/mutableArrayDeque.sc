import collection.mutable.ArrayDeque

val m = ArrayDeque(1, 2, 3, 4, 5)

println(m)

println(s"Removed head: ${m.removeHead()}, leaving $m")

println(s"Appended 6, giving ${m.append(6)}")

println(s"Removed head: ${m.removeHead()}, leaving $m")

println(s"Removed last: ${m.removeLast()}, leaving $m")

println(s"Freeze! ${m.to(Vector)}")

// In-place operations

println(m.mapInPlace(_ + 1))
println(m.filterInPlace(_ % 2 == 0))
