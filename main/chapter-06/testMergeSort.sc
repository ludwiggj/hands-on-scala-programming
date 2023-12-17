import org.scalatest.prop.TableDrivenPropertyChecks.*
import mergeSort.*
import cats.syntax.all._
import cats.Show

// Tests
val tests =
  Table(
    ("input", "expected"),
    (Array(1), Array(1)),
    (Array(2, 1), Array(1, 2)),
    (Array(4, 0, 1, 5, 2, 3), Array(0, 1, 2, 3, 4, 5))
  )

def runTests: Unit = {
  given showIntArray: Show[Array[Int]] = Show.show(ints => ints.map(_.show).mkString("Array(", ", ", ")"))

  forAll (tests) { (input: Array[Int], expected: Array[Int]) =>
    val actual = mergeSort(input)
    assert(
      actual sameElements expected,
      s"Sort: ${input.show}, Expected: ${expected.show}, Got: ${actual.show}"
    )
    println(s"Asserted: ${input.show} => ${actual.show}\n")
  }
}

runTests