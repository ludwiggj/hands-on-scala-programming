import org.scalatest.prop.TableDrivenPropertyChecks.*
import breadthFirstSearch.*

// Tests
val graph1 = Map(
    "a" -> Seq("b", "c"),
    "b" -> Seq("a"),
    "c" -> Seq("b")
)

val graph2 = Map(
    "a" -> Seq("b", "c"),
    "b" -> Seq("c", "d"),
    "c" -> Seq("d"),
    "d" -> Seq() 
)

val searchTests =
  Table(
    ("graph", "start", "expected"),
    (graph1, "c", Set("a", "b", "c")),
    (graph2, "a", Set("a", "b", "c", "d")),
    (graph2, "c", Set("c", "d"))
  )

def runSearchTests: Unit = {
  println("search")
  println("======")
  forAll (searchTests) { (graph: Map[String, Seq[String]], start: String, expected: Set[String]) =>
    val actual = search(start, graph)
    assert(
      actual == expected,
      s"Search: $start in $graph, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted : $start in $graph => $actual\n")
  }
}

def runSearchEmbeddedIfTests: Unit = {
  println("searchEmbeddedIf")
  println("================")
  forAll (searchTests) { (graph: Map[String, Seq[String]], start: String, expected: Set[String]) =>
    val actual = searchEmbeddedIf(start, graph)
    assert(
      actual == expected,
      s"SearchEmbeddedIf: $start in $graph, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted : $start in $graph => $actual\n")
  }
}

runSearchTests
runSearchEmbeddedIfTests