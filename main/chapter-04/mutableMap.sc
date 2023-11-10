import collection.mutable

val m = mutable.Map("one" -> 1, "two" -> 2, "three" -> 3)

println(m)
println(s"Removed key [two] (value [${m.remove("two")}]), leaving $m")
println(s"""Added (five -> 5) [${m("five") = 5}], giving $m""")
println(s"""Is key[three] there? (value [${m.getOrElseUpdate("three", -1)}]). giving $m""")
println(s"""Is key[four] there? (value [${m.getOrElseUpdate("four", -1)}]). giving $m""")