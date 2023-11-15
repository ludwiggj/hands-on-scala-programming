trait JsonParser[T] {
  def parse(s: String): T
}

object JsonParser {
  def parseFromString[T: JsonParser](s: String): T =
    summon[JsonParser[T]].parse(s)

  implicit object ParseInt extends JsonParser[Int] {
    def parse(s: String) = s.toInt
  }

  implicit object ParseBoolean extends JsonParser[Boolean] {
    def parse(s: String) = s.toBoolean
  }

  implicit object ParseDouble extends JsonParser[Double] {
    def parse(s: String) = s.toDouble
  }

  def parseSeqElements(s: String): Seq[String] = {
    def loop(s: String, elemAcc: String, seqAcc: Seq[String]): Seq[String] = {
      def isSeqContents(s: String): Boolean = !s.contains("[") && !s.contains("]")
  
      def isBalanced(s: String) = {
        def occurrences(ch: Char) = s.filter(_ == ch).length()
        occurrences('[') == occurrences(']')
      }  
  
      if (s.isEmpty()) {
        seqAcc
      } else {
        if (isSeqContents(s)) {
          s.split(',').toSeq
        } else {
          val nextChar = s.take(1)
          val remaining = s.drop(1)
          val updatedElemAcc = elemAcc + nextChar
          
          nextChar match
            // skip , if between balanced elements
            case "," if elemAcc.isEmpty() =>
              loop(remaining, elemAcc, seqAcc)
  
            // only check if the element is balanced on processing a ]  
            case "]" if isBalanced(updatedElemAcc) =>
              loop(remaining, "", seqAcc :+ updatedElemAcc)
  
            // otherwise add character to cureent element
            case _ =>
              loop(remaining, updatedElemAcc, seqAcc)
        }
      }
    }

    def dropBrackets(s: String): String = {
      def dropFirstOccurrence(s: String, target: Char): String = 
        if (!s.contains(target)) {
          s
        } else {
          s.dropWhile(_ != target).drop(1)
        }
  
      def dropLastOccurrence(s: String, target: Char): String =
        dropFirstOccurrence(s.reverse, target).reverse
  
      dropLastOccurrence(dropFirstOccurrence(s, '['), ']')
    } 

    loop(dropBrackets(s), "", Seq.empty)
  }

  implicit def ParseTuple[T, V](implicit p1: JsonParser[T], p2: JsonParser[V]): JsonParser[(T, V)] = new JsonParser[(T, V)] {
    def parse(s: String): (T, V) = {
      val Seq(left, right) = parseSeqElements(s)
      (p1.parse(left), p2.parse(right))
    }
  }

  implicit def ParseSeq[T](implicit p: JsonParser[T]): JsonParser[Seq[T]] = new JsonParser[Seq[T]] {
    def parse(s: String): Seq[T] =
      parseSeqElements(s).map(p.parse)
  }
}
