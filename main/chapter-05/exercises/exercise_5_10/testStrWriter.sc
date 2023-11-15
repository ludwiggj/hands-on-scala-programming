import org.scalatest.prop.TableDrivenPropertyChecks.*
import strWriter.*
import StrWriter.*

def runTest[T](input: T, write: T => String, expected: String) = {
  val actual = write(input)
  assert(
    actual == expected,
    s"Write: $input, Expected: $expected, Got: $actual"
  )
  println(s"Asserted: $input => $actual\n")
}

runTest(0, writeToString[Int], "0")
runTest(123, writeToString[Int], "123")
runTest(true, writeToString[Boolean], "true")
runTest(7.5d, writeToString[Double], "7.5")
runTest(Seq(true, false, true), writeToString[Seq[Boolean]], "[true,false,true]")
runTest(Seq(Seq(1), Seq(1, 2), Seq(1, 2, 3)), writeToString[Seq[Seq[Int]]], "[[1],[1,2],[1,2,3]]")
runTest(
  Seq(
    (Seq(1), Seq(true)),
    (Seq(2, 3), Seq(false, true)),
    (Seq(4, 5, 6), Seq(false, true, false))
  ),
  writeToString[Seq[(Seq[Int], Seq[Boolean])]],
  "[[[1],[true]],[[2,3],[false,true]],[[4,5,6],[false,true,false]]]"
)
runTest(
  Seq(
    (Seq(1), Seq((true, 0.5))),
    (Seq(2, 3), Seq((false, 1.5), (true, 2.5)))
  ),
  writeToString[Seq[(Seq[Int], Seq[(Boolean, Double)])]],
  "[[[1],[[true,0.5]]],[[2,3],[[false,1.5],[true,2.5]]]]"
)
