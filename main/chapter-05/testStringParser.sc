import org.scalatest.prop.TableDrivenPropertyChecks.*
import stringParser.*
import StrParser.*

val arguments = Seq("123", "true", "7.5")

// Tests
val tests =
  Table(
    ("input", "parse", "expected"),
    ("0", parseFromString[Int], 0),
    (arguments(0), parseFromString[Int], 123),
    (arguments(1), parseFromString[Boolean], true),
    (arguments(2), parseFromString[Double], 7.5d),   
    ("true,false,true", parseFromString[Seq[Boolean]], Seq(true, false, true)),
    ("1,2,3,4", parseFromString[Seq[Int]], Seq(1, 2, 3, 4)),
    ("1=false", parseFromString[(Int, Boolean)], (1, false)),
    ("true=2.9", parseFromString[(Boolean, Double)], (true, 2.9d)),
    (
        "1=true,2=false,3=true,4=false",
        parseFromString[Seq[(Int, Boolean)]],
        Seq(1 -> true, 2 -> false, 3 -> true, 4 -> false)
    ),
    (
        "1,2,3,4,5=true,false,true",
        parseFromString[(Seq[Int], Seq[Boolean])],
        (Seq(1, 2, 3, 4, 5) -> Seq(true, false, true))
    )
  )

def runTests: Unit = {
  forAll (tests) { (input: String, parse: String => AnyVal | AnyRef, expected: AnyVal | AnyRef) =>
    val actual = parse(input)
    assert(
      actual == expected,
      s"Parse: $input, Expected: $expected, Got: $actual"
    )
    println(s"Asserted: $input => $actual\n")
  }
}

runTests