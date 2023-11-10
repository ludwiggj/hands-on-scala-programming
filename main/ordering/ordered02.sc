import scala.collection.immutable.SortedSet

case class OrderedClass2(n: Int, name: String) extends Ordered[OrderedClass2] {
  def compare(that: OrderedClass2) = this.n - that.n

  def canEqual(a: Any) = a.isInstanceOf[OrderedClass2]

  override def equals(that: Any): Boolean =
    that match {
      case that: OrderedClass2 =>
        that.canEqual(this) &&
          this.n == that.n
      case _ => false
    }
  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + n
    result
  }
}

val one = OrderedClass2(1, "one")
val un = OrderedClass2(1, "un")
println(s"one == un: ${one == un}")

val english = Array(one, OrderedClass2(5, "five"), OrderedClass2(3, "three"))
println(s"english.head == english.head: ${english.head == english.head}")

val french = Array(un, OrderedClass2(5, "cinq"), OrderedClass2(3, "trois"))
println(s"englishArray == frenchArray: ${english sameElements french}")
println(s"englishList == frenchList: ${english.toList == french.toList}")

scala.util.Sorting.quickSort(english)
println(s"Sorted english: ${english.toList}")

val englishSortedSet = SortedSet.from(english)
println(s"englishSortedSet: $englishSortedSet")

val frenchSortedSet = SortedSet.from(french)
println(s"frenchSortedSet: $frenchSortedSet")

print(s"englishSortedSet == frenchSortedSet: ${englishSortedSet == frenchSortedSet}")
