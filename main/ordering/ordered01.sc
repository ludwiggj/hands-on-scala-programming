import scala.collection.immutable.SortedSet

case class OrderedClass1(n: Int, name: String) extends Ordered[OrderedClass1] {
  def compare(that: OrderedClass1) = this.n - that.n
  // No equals method provided, so inconsistent with compare
}

val one = OrderedClass1(1, "one")
val un = OrderedClass1(1, "un")
println(s"one == un: ${one == un}")

val english = Array(one, OrderedClass1(5, "five"), OrderedClass1(3, "three"))
println(s"english.head == english.head: ${english.head == english.head}")

val french = Array(un, OrderedClass1(5, "cinq"), OrderedClass1(3, "trois"))
println(s"englishArray == frenchArray: ${english sameElements french}")
println(s"englishList == frenchList: ${english.toList == french.toList}")

scala.util.Sorting.quickSort(english)
println(s"Sorted english: ${english.toList}")

val englishSortedSet = SortedSet.from(english)
println(s"englishSortedSet: $englishSortedSet")

val frenchSortedSet = SortedSet.from(french)
println(s"frenchSortedSet: $frenchSortedSet")

// Oh! See https://github.com/scala/bug/issues/10741
// And: https://www.scala-lang.org/api/2.12.7/scala/math/Ordered.html
print(s"englishSortedSet == frenchSortedSet: ${englishSortedSet == frenchSortedSet}")
