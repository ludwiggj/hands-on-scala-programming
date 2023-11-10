val myArray = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)

println(myArray.map(x => x + 1).filter(x => x % 2 == 0).slice(1, 3).mkString(","))

println(myArray.view.map(x => x + 1).filter(x => x % 2 == 0).slice(1, 3).mkString(","))