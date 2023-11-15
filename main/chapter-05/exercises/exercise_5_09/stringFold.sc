def toSeq(s: String): Seq[String] = {
    def notASeq(s: String): Boolean = !s.contains("[") && !s.contains("]")

    def dropFirstOccurrence(s: String, target: Char): String = 
        if (!s.contains(target)) {
            s
        } else {
            s.dropWhile(_ != target).drop(1)
        }

    def dropLastOccurrence(s: String, target: Char): String =
        dropFirstOccurrence(s.reverse, target).reverse

    def dropBrackets(s: String): String =
        dropLastOccurrence(dropFirstOccurrence(s, '['), ']')

    def isBalanced(s: String) = {
        def occurrences(ch: Char) =
            s.filter(_ == ch).length()

        occurrences('[') == occurrences(']')
    }
    
    def loop[T](s: String, elemAcc: String, seqAcc: Seq[String]): Seq[String] = {
        // println(s"Loop: $s")
        if (s.isEmpty()) {
            seqAcc
        } else {
            if (notASeq(s)) {
                s.split(',').toSeq
            } else {
                val nextChar = s.take(1)
                val remaining = s.drop(1)
                val elem = elemAcc + nextChar

                nextChar match
                    case "," if elemAcc.isEmpty() =>
                        loop(remaining, "", seqAcc)

                    case "]" if isBalanced(elem) =>
                        //val recurseElem = toSeq(elem)
                        //println(s"Recurse: elem: $elem seqAcc: $seqAcc recurseElem: $recurseElem")
                        loop(remaining, "", seqAcc :+ elem)

                    case _ => 
                        loop(remaining, elem, seqAcc)
            }
        }
    }

    loop(dropBrackets(s), "", Seq.empty)
}

val s0 = toSeq("[1,2,3]")
println(s"${s0.size}, $s0")

val s1 = toSeq("[[1],[1,2],[1,2,3]]")
println(s"${s1.size}, $s1")

val s2 = toSeq("[[[1],[true]],[[2,3],[false,true]],[[4,5,6],[false,true,false]]]")
println(s"${s2.size}, $s2")

val s3 = toSeq("[[[1],[[true,0.5]]],[[2,3],[[false,1.5],[true,2.5]]]]")
println(s"${s3.size}, $s3")

/*
println(s"dfo: <${dropFirstOccurrence("1, 2, 3, ", '[')}>")
println(s"dfo: <${dropFirstOccurrence("[1, 2, 3, [    ", '[')}>")
println(s"dfo: <${dropFirstOccurrence("  [1, 2, 3, [  ", '[')}>")
println(s"dfo: <${dropFirstOccurrence("1, 2, 3, [", '[')}>")

println(s"dlo: <${dropLastOccurrence("1, 2, 3, ", '[')}>")
println(s"dlo: <${dropLastOccurrence("[1, 2, 3, [    ", '[')}>")
println(s"dlo: <${dropLastOccurrence("  [1, 2, 3, [  ", '[')}>")
println(s"dlo: <${dropLastOccurrence("1, 2, 3, [", '[')}>")
*/
