import collection.mutable

val s = mutable.Set(1, 2, 3)

println(s)

s.add(4)

println(s"Added 4, giving $s")

s.remove(1)

println(s"Removed 1, giving $s")

val frozenS = s.to(Set)

println(s"Freeze! $frozenS")

println(s"Added 5, giving ${frozenS + 5}")

val ss = frozenS.to(mutable.Set)

println(s"Thawed! $ss")