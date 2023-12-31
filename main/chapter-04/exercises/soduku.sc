def isValidSudoku(grid: Array[Array[Int]]): Boolean = {
  !Range(0, 9).exists { i =>
    val row = Range(0, 9).map(grid(i)(_)).filter(_ > 0)
    val col = Range(0, 9).map(grid(_)(i)).filter(_ > 0)
    val square = Range(0, 9).map(j => grid((i % 3) * 3 + j % 3)((i / 3) * 3 + j / 3)).filter(_ > 0)
    row.distinct.length != row.length ||
      col.distinct.length != col.length ||
      square.distinct.length != square.length
  }
}

def renderSoduku(grid: Array[Array[Int]]): String = {
  val lineSep = "\n"
  val blockDivider = "+-------+-------+-------+"

  def renderRow(row: Array[Int]): String = {
    row.map { cell =>
        if (cell == 0) " " else cell.toString
      }
      .grouped(3)
      .map(_.mkString(" "))
      .mkString("| ", " | ", " |")
  }

  def renderRowBlock(rowBlock: Array[Array[Int]]): String = {
    rowBlock
      .map(renderRow)
      .mkString(lineSep)
  }

  grid
    .grouped(3)
    .map(renderRowBlock)
    .mkString(
      s"$blockDivider$lineSep",
      s"$lineSep$blockDivider$lineSep",
      s"$lineSep$blockDivider"
    )
}

val validSoduku = Array(
  Array(3, 1, 6, 5, 7, 8, 4, 9, 2),
  Array(5, 2, 9, 1, 3, 4, 7, 6, 8),
  Array(4, 8, 7, 6, 2, 9, 5, 3, 1),
  Array(2, 6, 3, 0, 1, 0, 0, 8, 0),
  Array(9, 7, 4, 8, 6, 3, 0, 0, 5),
  Array(8, 5, 1, 0, 9, 0, 6, 0, 0),
  Array(1, 3, 0, 0, 0, 0, 2, 5, 0),
  Array(0, 0, 0, 0, 0, 0, 0, 7, 4),
  Array(0, 0, 5, 2, 0, 6, 3, 0, 0)
)

println(s"${renderSoduku(validSoduku)}\nshould be valid")

assert(isValidSudoku(validSoduku), "soduku should be valid")

val invalidSoduku = Array(
  Array(3, 1, 6, 5, 7, 8, 4, 9, 3),
  Array(5, 2, 9, 1, 3, 4, 7, 6, 8),
  Array(4, 8, 7, 6, 2, 9, 5, 3, 1),
  Array(2, 6, 3, 0, 1, 0, 0, 8, 0),
  Array(9, 7, 4, 8, 6, 3, 0, 0, 5),
  Array(8, 5, 1, 0, 9, 0, 6, 0, 0),
  Array(1, 3, 0, 0, 0, 0, 2, 5, 0),
  Array(0, 0, 0, 0, 0, 0, 0, 7, 4),
  Array(0, 0, 5, 2, 0, 6, 3, 0, 0)
)

println(s"${renderSoduku(invalidSoduku)}\nshould be invalid")

assert(!isValidSudoku(invalidSoduku), "soduku should be invalid")