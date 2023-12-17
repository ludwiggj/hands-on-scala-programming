import org.scalatest.prop.TableDrivenPropertyChecks.*
import genericMergeSort.*
import cats.syntax.all._
import cats.Show

given showIndexedSeq[T: Show]: Show[IndexedSeq[T]] =
  Show.show(_.map(_.show).mkString("Seq(", ", ", ")"))

def runTest[T: Ordering: Show](input: IndexedSeq[T], expected: IndexedSeq[T]) = {
  val actual = mergeSort(input)
    assert(
      actual sameElements expected,
      s"Sort: ${input.show}, Expected: ${expected.show}, Got: ${actual.show}"
    )
    println(s"Asserted: ${input.show} => ${actual.show}\n")
}


def runTests: Unit = {
  runTest(Array(1), Array(1))
  runTest(Array(2, 1), Array(1, 2))
  runTest(Array(4, 0, 1, 5, 2, 3), Array(0, 1, 2, 3, 4, 5))
  runTest(Array(5, 3, 4, 2, 1), Vector(1, 2, 3, 4, 5))
  runTest(Vector(5, 4, 3, 2, 1), Vector(1, 2, 3, 4, 5))
  runTest(Vector("banana", "apple", "durian", "cabbage"), Vector("apple", "banana", "cabbage", "durian"))
}

runTests