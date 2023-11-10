import scala.collection.immutable.SortedSet
import scala.math.Ordered.orderingToOrdered

// https://stackoverflow.com/questions/10602730/scala-ordered-by-multiple-fields
case class ANumber(n: Int, name: String)

object Orderings {
  implicit val orderByAllFields: Ordering[ANumber] = Ordering.by[ANumber, Int](_.n).orElseBy[String](_.name)
  implicit val orderByNumber: Ordering[ANumber] = Ordering.by[ANumber, Int](_.n)
  implicit val orderByName: Ordering[ANumber] = Ordering.by[ANumber, String](_.name)
}

val english = Array(ANumber(1, "one"), ANumber(5, "five"), ANumber(3, "three"))
val french = Array(ANumber(1, "un"), ANumber(5, "cinq"), ANumber(3, "trois"))

def runOrderByAllFields(): Unit = {
  import Orderings.orderByAllFields

  println("Ordering by all fields...")
  println(s"englishArray == frenchArray: ${english sameElements french}")
  println(s"englishList == frenchList: ${english.toList == french.toList}")

  val englishSortedSet = SortedSet.from(english)
  println(s"englishSortedSet: $englishSortedSet")

  val frenchSortedSet = SortedSet.from(french)
  println(s"frenchSortedSet: $frenchSortedSet")

  // Oh! See https://github.com/scala/bug/issues/10741
  println(s"englishSortedSet == frenchSortedSet: ${englishSortedSet == frenchSortedSet}")
}

def runOrderByNumber(): Unit = {
  import Orderings.orderByNumber

  println("Ordering by number...")
  println(s"englishArray == frenchArray: ${english sameElements french}")
  println(s"englishList == frenchList: ${english.toList == french.toList}")

  val englishSortedSet = SortedSet.from(english)
  println(s"englishSortedSet: $englishSortedSet")

  val frenchSortedSet = SortedSet.from(french)
  println(s"frenchSortedSet: $frenchSortedSet")

  // Oh! See https://github.com/scala/bug/issues/10741
  println(s"englishSortedSet == frenchSortedSet: ${englishSortedSet == frenchSortedSet}")
}

def runOrderByName(): Unit = {
  import Orderings.orderByName

  println("Ordering by name...")
  println(s"englishArray == frenchArray: ${english sameElements french}")
  println(s"englishList == frenchList: ${english.toList == french.toList}")

  val englishSortedSet = SortedSet.from(english)
  println(s"englishSortedSet: $englishSortedSet")

  val frenchSortedSet = SortedSet.from(french)
  println(s"frenchSortedSet: $frenchSortedSet")

  // Oh! See https://github.com/scala/bug/issues/10741
  println(s"englishSortedSet == frenchSortedSet: ${englishSortedSet == frenchSortedSet}")
}

runOrderByAllFields()
runOrderByNumber()
runOrderByName()