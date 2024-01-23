import org.scalatest.prop.TableDrivenPropertyChecks.*
import shortestPath.*

val flippedGraph = Map(
    "a" -> Seq("c", "b"),
    "b" -> Seq("c", "d"),
    "c" -> Seq("d"),
    "d" -> Seq() 
)

// Tests
val shortestPathTests =
  Table(
    ("graph", "start", "dest", "expected"),
    (graph, "a", "d", List("a", "b", "d")),
    (flippedGraph, "a", "d", List("a", "c", "d")),
    (graph, "a", "c", List("a", "c")),
    (flippedGraph, "a", "c", List("a", "c"))
  )

def runShortestPathTests: Unit = {
  println("shortest path")
  println("=============")
  forAll (shortestPathTests) { (graph: Map[String, Seq[String]], start: String, dest: String, expected: List[String]) =>
    val actual = shortestPath(start, dest, graph)
    assert(
      actual == expected,
      s"Shortest path from $start to $dest in $graph, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted : $start to $dest in $graph => $actual\n")
  }
}

runShortestPathTests