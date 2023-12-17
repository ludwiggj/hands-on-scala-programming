import org.scalactic.Bool
import org.scalatest.prop.TableDrivenPropertyChecks.*
import trieSet.*

// Tests
val t = TrieSet()
t.add("mango")
t.add("mandarin")
t.add("map")
t.add("man")

val containTests =
  Table(
    ("input", "expected"),
    ("mango", true),
    ("mang", false),
    ("man", true),
    ("mandarin", true),
    ("mandarine", false)
  )

def runContainTests: Unit = {
  println("contains")
  println("========")
  forAll (containTests) { (input: String, expected: Boolean) =>
    val actual = t.contains(input)
    assert(
      actual == expected,
      s"Contains: $input, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted : $input => $actual\n")
  }
}

val prefixesMatchingStringTests =
  Table(
    ("input", "expected"),
    ("mandible", Set("man")),
    ("mangosteen", Set("man", "mango"))
  )

def runPrefixesMatchingStringTests: Unit = {
  println("prefixesMatchingString")
  println("=====================")
  forAll (prefixesMatchingStringTests) { (input: String, expected: Set[String]) =>
    val actual = t.prefixesMatchingString(input)
    assert(
      actual == expected,
      s"prefixesMatchingString: $input, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted: $input => $actual\n")
  }
}

val stringsMatchingPrefixTests =
  Table(
    ("input", "expected"),
    ("man", Set("man", "mango", "mandarin")),
    ("ma", Set("man", "map", "mango", "mandarin")),
    ("map", Set("map")),
    ("mand", Set("mandarin")),
    ("mando", Set())
  )

def runStringsMatchingPrefixTests: Unit = {
  println("stringsMatchingPrefix")
  println("=====================")
  forAll (stringsMatchingPrefixTests) { (input: String, expected: Set[String]) =>
    val actual = t.stringsMatchingPrefix(input)
    assert(
      actual == expected,
      s"stringsMatchingPrefix: $input, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted: $input => $actual\n")
  }
}

runContainTests
runPrefixesMatchingStringTests
runStringsMatchingPrefixTests