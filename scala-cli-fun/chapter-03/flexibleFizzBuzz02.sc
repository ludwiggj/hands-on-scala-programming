def flexibleFizzBuzz(handleLine: String => Unit) = 
  for (i <- Range.inclusive(1, 15)) {
    handleLine(
      if (i % 3 == 0 && i % 5 == 0) "FizzBuzz"
      else if (i % 3 == 0) "Fizz"
      else if (i % 5 == 0) "Buzz"
      else i.toString
    )
  }

flexibleFizzBuzz(s => {}) // do nothing
flexibleFizzBuzz(s => println(s))
  
var i = 0
val output = new Array[String](15)
flexibleFizzBuzz{s =>
  output(i) = s
  i += 1
}
output.foreach(println)  