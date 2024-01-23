import scala.annotation.tailrec
import math.Ordered.orderingToOrdered

def binarySearchOld[T: Ordering](sorted: IndexedSeq[T], target: T): Boolean = {
    println(">>> " + sorted.size)
    sorted.size match {
        case 0 => false
        case 1 => sorted(0) == target
        case x => binarySearch(sorted.take(x / 2), target) || binarySearch(sorted.drop(x / 2), target)
    }
}

@tailrec
def binarySearch[T: Ordering](sorted: IndexedSeq[T], target: T): Boolean = {
    println(s"Searching $sorted")
    val size = sorted.size
    if (size == 0)
        false
    else
        val midPoint = size / 2
        val middleValue = sorted(midPoint)
        println(s"Testing $middleValue")
        if (middleValue == target)
          true
        else
            if (target < middleValue)
              binarySearch(sorted.take(midPoint), target)
            else 
              binarySearch(sorted.drop(midPoint + 1), target)
}