import org.scalatest.prop.TableDrivenPropertyChecks.*
import binarySearch.*

// Tests
val binarySearchTests =
  Table(
    ("array", "target", "expected"),
    (Array[Int](), 1, false),
    (Array(1), 1, true),
    (Array(2), 1, false),
    (Array(2, 4, 6, 8, 10, 12), 5, false),
    (Array(2, 4, 6, 8, 10, 12), 10, true)
  )

def runBinarySearchTests: Unit = {
    def showArr(arr: Array[Int]): String = "Array" + arr.map(_.toString).mkString("(", ", ", ")")
    
    println("search")
    println("======")

    println(showArr(Array(1, 2, 3)))

    forAll (binarySearchTests) { (arr: Array[Int], target: Int, expected: Boolean) =>
        val arrAsString = showArr(arr)
        val actual = binarySearch(arr, target)
        assert(
            actual == expected,
            s"Search: $target in $arrAsString, Expected: $expected, Got: $actual"
        )
        println(s"  Asserted : $target in $arrAsString => $actual\n")
    }
}

runBinarySearchTests