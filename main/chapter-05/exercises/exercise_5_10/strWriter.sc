trait StrWriter[T] {
  def write(repr: T): String
}

object StrWriter {
  def writeToString[T: StrWriter](repr: T): String =
    summon[StrWriter[T]].write(repr)

  implicit object IntWriter extends StrWriter[Int] {
    def write(repr: Int) = repr.toString()
  }

  implicit object BooleanWriter extends StrWriter[Boolean] {
    def write(repr: Boolean) = repr.toString()
  }

  implicit object DoubleWriter extends StrWriter[Double] {
    def write(repr: Double) = repr.toString()
  }

  implicit def WriteSeq[T](implicit s: StrWriter[T]): StrWriter[Seq[T]] = new StrWriter[Seq[T]] {
    def write(repr: Seq[T]) = repr.map(s.write).mkString("[", ",", "]")
  }

  implicit def WriteTuple[T, V](implicit s1: StrWriter[T], s2: StrWriter[V]): StrWriter[(T, V)] = new StrWriter[(T, V)] {
    def write(repr: (T, V)) = {
      val (left, right) = repr
      s"[${s1.write(left)},${s2.write(right)}]"
    }
  }
}
