import org.scalatest.prop.TableDrivenPropertyChecks.*
import jsonParser.*
import JsonParser.*

val tests =
  Table(
    ("input", "parse", "expected"),
    ("0", parseFromString[Int], 0),
    ("123", parseFromString[Int], 123),
    ("true", parseFromString[Boolean], true),
    ("7.5", parseFromString[Double], 7.5d),
    ("[true,false,true]", parseFromString[Seq[Boolean]], List(true, false, true)),
    ("[[1],[1,2],[1,2,3]]", parseFromString[Seq[Seq[Int]]], List(List(1), List(1, 2), List(1, 2, 3))),
    (
      "[[[1],[true]],[[2,3],[false,true]],[[4,5,6],[false,true,false]]]",
      parseFromString[Seq[(Seq[Int], Seq[Boolean])]],
      List(
        (List(1), List(true)),
        (List(2, 3), List(false, true)),
        (List(4, 5, 6), List(false, true, false))
      )
    ),
    (
      "[[[1],[[true,0.5]]],[[2,3],[[false,1.5],[true,2.5]]]]",
      parseFromString[Seq[(Seq[Int], Seq[(Boolean, Double)])]],
      List(
        (List(1), List((true, 0.5))),
        (List(2, 3), List((false, 1.5), (true, 2.5)))
      )
    )
  )

def runTests: Unit = {
  forAll (tests) { (input: String, parse: String => Any, expected: Any) =>
    val actual = parse(input)
    assert(
      actual == expected,
      s"Parse: $input, Expected: $expected, Got: $actual"
    )
    println(s"Asserted: $input => $actual\n")
  }
}

runTests