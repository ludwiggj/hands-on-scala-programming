def isValidSudoku(grid: Array[Array[Int]]): Boolean = {
    !Range(0, 9).exists{i =>
      val row = Range(0, 9).map(grid(i)(_))
      val col = Range(0, 9).map(grid(_)(i))
      val square = Range(0, 9).map(j => grid((i % 3) * 3 + j % 3)((i / 3) * 3 + j / 3))
      row.distinct.length != row.length ||
      col.distinct.length != col.length ||
      square.distinct.length != square.length
} }

println(isValidSudoku(Array(
  Array(5, 3, 4, 6, 7, 8, 9, 1, 2),
  Array(6, 7, 2, 1, 9, 5, 3, 4, 8),
  Array(1, 9, 8, 3, 4, 2, 5, 6, 7),
  Array(8, 5, 9, 7, 6, 1, 4, 2, 3),
  Array(4, 2, 6, 8, 5, 3, 7, 9, 1),
  Array(7, 1, 3, 9, 2, 4, 8, 5, 6),
  Array(9, 6, 1, 5, 3, 7, 2, 8, 4),
  Array(2, 8, 7, 4, 1, 9, 6, 3, 5),
  Array(3, 4, 5, 2, 8, 6, 1, 7, 9)
)))

println(isValidSudoku(Array(
  Array(5, 3, 4, 6, 7, 8, 9, 1, 2),
  Array(6, 7, 2, 7, 6, 1, 4, 2, 3),
  Array(1, 9, 8, 5, 3, 7, 2, 8, 4),
  Array(8, 5, 9, 1, 9, 5, 3, 4, 8),
  Array(4, 2, 6, 8, 5, 3, 7, 9, 1),
  Array(7, 1, 3, 4, 1, 9, 6, 3, 5),
  Array(9, 6, 1, 3, 4, 2, 5, 6, 7),
  Array(2, 8, 7, 9, 2, 4, 8, 5, 6),
  Array(3, 4, 5, 2, 8, 6, 1, 7, 8) 
)))