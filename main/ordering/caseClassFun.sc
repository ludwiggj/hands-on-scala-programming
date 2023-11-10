case class ANumber private(n: Int, name: String) {
//  override def copy(
//                     n: Int = this.n,
//                     name: String = this.name
//                   ): ANumber = ANumber(n, name)
}

object ANumber {
  def apply(n: Int, name: String): ANumber = n % 2 match {
    case 0 =>
      new ANumber(n, s"$name is even")
    case 1 =>
      new ANumber(n, s"$name is odd")
  }
}

val one = ANumber(1, "one")

println(one)

println(one.copy(n = 2))