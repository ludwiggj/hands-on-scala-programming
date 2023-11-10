val grouped = Array(1, 2, 3, 4, 5, 6, 7).groupBy(_ % 2)

grouped.foreach {
    case (k, v) => println(s"Key [$k] Values [${v.to(Seq)}]")
}

for ((k, v) <- grouped) println(s"Key [$k] Values [${v.to(Seq)}]")