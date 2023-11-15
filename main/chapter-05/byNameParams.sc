// wrapping operation to measure evaluation time
def measureTime(f: => Unit) = {
  val start = System.currentTimeMillis()
  f
  val end = System.currentTimeMillis()
  println("Evaluation took " + (end - start) + " milliseconds")
}

measureTime(new Array[String](10 * 1000 * 1000).hashCode())
measureTime { // methods taking a single arg can also be called with curly brackets
  new Array[String](100 * 1000 * 1000).hashCode()
}

// wrapping operation to handle errors
def doubleSafely(f: => Int) =
  try {
    println(f * 2)
  } catch
    case e: Exception =>
      println(s"Oh dear, ${e.getMessage}")

doubleSafely(5)
doubleSafely(1 / 0)

def double(f: Int) = println(f * 2)

double(5)
// double(1 / 0) // java.lang.ArithmeticException

// wrapping operation for repeated evaluation
def retry[T](max: Int)(f: => T): T = {
  var tries = 0
  var result: Option[T] = None
  while (result.isEmpty) {
    try {
      result = Some(f)
      println(s"Yes! Got result = $result")
      result
    }
    catch {
      case e: Throwable =>
        tries += 1
        if (tries > max) throw e
        else {
          println(s"failed, retry #$tries")
        }
    }
  }
  result.get
}

val httpbin = "https://httpbin.org"
retry(max = 3) {
  // Only succeeds with a 200 response
  // code 1/3 of the time
  requests.get(
    s"$httpbin/status/200,400,500"
  )
}