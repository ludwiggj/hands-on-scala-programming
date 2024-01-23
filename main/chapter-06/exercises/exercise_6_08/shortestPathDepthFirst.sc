def searchPaths[T](start: T, graph: Map[T, Seq[T]]): Map[T, Seq[T]] = {
  val seen = collection.mutable.Map(start -> List(start))
  val toProcess = collection.mutable.ListBuffer(start -> List(start))

  println(s"searching paths (depth first) for start point\n[$start]\nin\n$graph")
  while (toProcess.nonEmpty) {
    println(s"  outer toProcess: $toProcess")
    println(s"             seen: $seen")
    val (current, path) = toProcess.remove(0)
    println(s"       processing: $current")
    println(s"   left toProcess: $toProcess")
    for (next <- graph(current).reverse if !seen.contains(next)) {
      println(s"         new node: $next")
      val newPath = next :: path
      seen(next) = newPath
      toProcess.prepend(next -> newPath)
      println(s"updated toProcess: $toProcess")
    }
  }
  seen.toMap
}

def shortestPath[T](start: T, dest: T, graph: Map[T, Seq[T]]): Seq[T]= {
  val shortestReversedPaths = searchPaths(start, graph)
  shortestReversedPaths(dest).reverse
}

// processing order: a b d c
// val graph = Map(
//     "a" -> Seq("b", "c"),
//     "b" -> Seq("c", "d"),
//     "c" -> Seq("d"),
//     "d" -> Seq() 
// )


// processing order: a b d e c
val graph = Map(
    "a" -> Seq("b", "c"),
    "b" -> Seq("d"),
    "c" -> Seq(),
    "d" -> Seq("e"),
    "e" -> Seq()
)

val start = "a"

println(s"search paths (depth first) for start point\n[$start]\nin\n$graph\nare\n${searchPaths(start, graph)}")