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

  def notASeq(s: String): Boolean = !s.contains("[") && !s.contains("]")

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

  def isBalanced(s: String) = {
          def occurrences(ch: Char) = s.filter(_ == ch).length()
          occurrences('[') == occurrences(']')
  }    

  def loop(s: String, elemAcc: String, seqAcc: Seq[String]): Seq[String] = {
        if (s.isEmpty()) {
          seqAcc
        } else {
          if (notASeq(s)) {
            s.split(',').toSeq
          } else {
            val nextChar = s.take(1)
            val remaining = s.drop(1)
            val elem = elemAcc + nextChar

            //println(s"Char: <$nextChar> elemAcc: <$elemAcc> elem <$elem> isBalanced <${isBalanced(elem)}>")
            nextChar match
              case "," if elemAcc.isEmpty() =>
                //println(s"Discarding ,")
                loop(remaining, "", seqAcc)

              case "]" if isBalanced(elem) =>
                // println(s"Recursing: elem: $elem seqAcc: $seqAcc")
                // val recurseElem = parseFromString(dropBrackets(elem))
                // println(s"Recursing: elem: recurseElem: $recurseElem")

                loop(remaining, "", seqAcc :+ elem)

              case x =>
                //println(s"Taking $x")
                loop(remaining, elem, seqAcc)
          }
        }
      }

  implicit def ParseTuple[T, V](implicit p1: JsonParser[T], p2: JsonParser[V]): JsonParser[(T, V)] = new JsonParser[(T, V)] {
    def parse(s: String): (T, V) = {
      println(s"About to parse as TUPLE: $s")
      val adjustedS = dropBrackets(s)

      // notASeq slight misnomer here
      // if (notASeq(adjustedS)) {
        // val Array(left, right) = adjustedS.split(",")
        // (p1.parse(left), p2.parse(right))
      // } else {
      val Array(left, right) = loop(adjustedS, "", Seq.empty).toArray
      (p1.parse(left), p2.parse(right))
      // }
    }
  }

  // (1) Two layers:
  //   [[1],[1,2],[1,2,3]]
  // => [Seq[Seq[Int]]]
  // => List(List(1), List(1, 2), List(1, 2, 3))
  // Next level: [1],[1,2],[1,2,3]
  //   [1] | [1,2] | [1,2,3]

  // (2) Three layers:
  //   [[[1],[true]],[[2,3],[false,true]],[[4,5,6],[false,true,false]]]
  // => Seq[(Seq[Int], Seq[Boolean])]
  // => List(
  //      (List(1), List(true)),
  //      (List(2, 3), List(false, true)),
  //      (List(4, 5, 6), List(false, true, false))
  //    )
  // Next Level: [[1],[true]],[[2,3],[false,true]],[[4,5,6],[false,true,false]]
  //   [[1],[true]] | [[2,3],[false,true]] | [[4,5,6],[false,true,false]]

  // (3) Four layers:
  //    [[[1],[[true,0.5]]],[[2,3],[[false,1.5],[true,2.5]]]]
  // => Seq[(Seq[Int], Seq[(Boolean, Double)])]
  // => List(
  //      (List(1), List((true, 0.5))),
  //      (List(2, 3), List((false, 1.5), (true, 2.5)))
  //    )

  // Breakdown: Seq[(Seq[Int], Seq[(Boolean, Double)])]
  // => [[[1],[[true,0.5]]],[[2,3],[[false,1.5],[true,2.5]]]]
  //    [[1],[[true,0.5]]],[[2,3],[[false,1.5],[true,2.5]]]   // strip outer brackets
  //    => [[1],[[true,0.5]]]
  //       [1],[[true,0.5]]                                   // strip outer brackets
  //       => [1]
  //       => [[true,0.5]]
  //          [true,0.5]                                      // strip outer brackets
  //    => [[2,3],[[false,1.5],[true,2.5]]]
  //       [2,3],[[false,1.5],[true,2.5]]                     // strip outer brackets
  //        => [2,3]
  //        => [[false,1.5],[true,2.5]]
  //           [false,1.5],[true,2.5]                         // strip outer brackets
  //           => [false,1.5]
  //           => [true,2.5]

  implicit def ParseSeq[T](implicit p: JsonParser[T]): JsonParser[Seq[T]] = new JsonParser[Seq[T]] {
    def parse(s: String): Seq[T] = {  //s.split(',').toSeq.map(p.parseFromString)
      println(s"About to parse as SEEQ: $s")
      loop(dropBrackets(s), "", Seq.empty).map(parseFromString)
    }
  }
}
