import org.scalactic.Bool
import org.scalatest.prop.TableDrivenPropertyChecks.*
import trieMap.*

// Tests
val t = TrieMap[Int]()
t.add("mango", 1337)
t.add("mandarin", 31337)
t.add("map", 37)
t.add("man", 7)

def runContainTests: Unit = {
  println("contains")
  println("========")

  val containTests =
    Table(
      ("input", "expected"),
      ("mango", true),
      ("mang", false),
      ("man", true),
      ("mandarin", true),
      ("mandarine", false)
    )

  forAll (containTests) { (input: String, expected: Boolean) =>
    val actual = t.contains(input)
    assert(
      actual == expected,
      s"Contains: $input, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted : $input => $actual\n")
  }
}

def runGetTests: Unit = {
  println("get")
  println("===")

  val getTests =
    Table(
      ("input", "expected"),
      ("mango", Some(1337)),
      ("mang", None),
      ("man", Some(7)),
      ("mandarin", Some(31337)),
      ("mandarine", None)
    )

  forAll (getTests) { (input: String, expected: Option[Int]) =>
    val actual = t.get(input)
    assert(
      actual == expected,
      s"Get: $input, Expected: $expected, Got: $actual"
    )
    println(s"  Asserted : $input => $actual\n")
  }
}

/*
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
*/
runContainTests
runGetTests
// runPrefixesMatchingStringTests
// runStringsMatchingPrefixTests