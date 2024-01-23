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

def solveSoduku(grid: Array[Array[Int]]): Option[Array[Array[Int]]] = {
  def topLeft(x: Int, y: Int): (Int, Int) =
    ((x / 3) * 3, (y / 3) * 3)

  def sameRow(y: Int, x: Int): Array[Int] = grid(y).filter(_ > 0).filter(_ != grid(y)(x))

  def sameColumn(y: Int, x: Int): Array[Int] = Range(0, 9).map(grid(_)(x)).filter(_ > 0).filter(_ != grid(y)(x)).toArray

  def sameBox(y: Int, x: Int): Array[Int] = {
    val (xTopLeft, yTopLeft) = topLeft(x, y)
    (
      for {
        y <- Range(yTopLeft, yTopLeft + 3)
        x <- Range(xTopLeft, xTopLeft + 3)
      } yield grid(y)(x)
    ).filter(_ > 0).filter(_ != grid(y)(x)).toArray
  }

  def candidates(y: Int, x: Int): Array[Int] = {
    val taken = (sameRow(y, x) ++ sameColumn(y, x) ++ sameBox(y, x)).toSet
    (1 to 9).filterNot(taken.contains).toArray
  }

  def renderArray(a: Array[Int]) = a.mkString("(", ", ", ")")

  println(renderSoduku(grid))

  if (isValidSudoku(grid)) {
    // solve it
    (for {
      y <- Range(0, 9)
      x <- Range(0, 9)
    } yield(
      s"grid(x=$x, y=$y) = ${grid(y)(x)}" + (
        if (grid(y)(x) == 0) then {
          s"\n     topLeft = ${topLeft(x, y)}" +
          s"\n     sameRow = ${renderArray(sameRow(y, x))}" +
          s"\n     sameCol = ${renderArray(sameColumn(y, x))}" +
          s"\n     sameBox = ${renderArray(sameBox(y, x))}" +
          s"\n  candidates = ${renderArray(candidates(y, x))}"
        } else
          ""
      )
    )
    ).foreach(println)
    None
  } else
    None
  }
