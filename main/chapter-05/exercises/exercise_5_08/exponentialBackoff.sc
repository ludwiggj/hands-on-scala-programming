import scala.math.pow

// wrapping operation for repeated evaluation
// delay is in mS
def retry[T](max: Int, delay: Int)(f: => T): T = {
  var tries = 0
  var result: Option[T] = None
  while (result.isEmpty) {
    try {
      result = Some(f)
      println(s"+++ Yes! Got result = $result")
      result
    }
    catch {
      case e: Throwable =>
        tries += 1
        if (tries > max) throw e
        else {
          println(s"failed, retry #$tries")
          val sleepDurationMilliSeconds = delay * pow(2, (tries - 1)).toLong
          println(s"+++ sleeping for $sleepDurationMilliSeconds milliseconds")
          Thread.sleep(sleepDurationMilliSeconds)
        }
    }
  }
  result.get
}

val httpbin = "https://httpbin.org"
retry(max = 50, delay = 100) {
  // Only succeeds with a 200 response
  // code 1/3 of the time
  requests.get(
    s"$httpbin/status/200,400,500"
  )
}