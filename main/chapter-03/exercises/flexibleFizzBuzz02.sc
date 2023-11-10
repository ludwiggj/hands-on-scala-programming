def textbookFlexibleFizzBuzz(handleLine: String => Unit): Unit =
  for (i <- Range.inclusive(1, 15)) {
    handleLine(
      if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
      else if (i % 3 == 0) "Fizz"
      else if (i % 5 == 0) "Buzz"
      else i.toString
    )
  }

textbookFlexibleFizzBuzz(_ => {}) // do nothing
textbookFlexibleFizzBuzz(s => println(s))

var i = 0
val output = new Array[String](15)
textbookFlexibleFizzBuzz{ s =>
  output(i) = s
  i += 1
}
output.foreach(println)  