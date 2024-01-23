def searchPaths[T](start: T, graph: Map[T, Seq[T]]): Map[T, Seq[T]] = {
  val seen = collection.mutable.Map(start -> List(start))
  val toProcess = collection.mutable.ArrayDeque(start -> List(start))

  println(s"searching paths (breadth first) for start point\n[$start]\nin\n$graph")

  while (toProcess.nonEmpty) {
    println(s"  outer toProcess: $toProcess")
    println(s"             seen: $seen")
    val (current, path) = toProcess.removeHead()
    println(s"       processing: $current")
    println(s"   left toProcess: $toProcess")
    for (next <- graph(current) if !seen.contains(next)) {
      println(s"         new node: $next")
      val newPath = next :: path
      seen(next) = newPath
      toProcess.append(next -> newPath)
      println(s"updated toProcess: $toProcess")
    }
  }
  seen.toMap
}

def shortestPath[T](start: T, dest: T, graph: Map[T, Seq[T]]): Seq[T]= {
  val shortestReversedPaths = searchPaths(start, graph)
  shortestReversedPaths(dest).reverse
}

// processing order: a b c d e
// val graph = Map(
//     "a" -> Seq("b", "c"),
//     "b" -> Seq("d"),
//     "c" -> Seq(),
//     "d" -> Seq("e"),
//     "e" -> Seq()
// )

// processing order: a b c d
val graph = Map(
    "a" -> Seq("b", "c"),
    "b" -> Seq("c", "d"),
    "c" -> Seq("d"),
    "d" -> Seq() 
)

val start = "a"

println(s"search paths (breadth first) for start point\n[$start]\nin\n$graph\nare\n${searchPaths(start, graph)}")