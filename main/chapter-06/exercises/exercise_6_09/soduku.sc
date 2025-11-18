import exercises.exercise_6_10.testTrieMap_sc

case class Cell(x: Int, y: Int)

case class Soduku(grid: Array[Array[Int]]) {
  def completed(data: List[Int]): List[Int] = data.filter(_ > 0)

  def getRow(y: Int): List[Int] = completed(grid(y).toList)

  def getCell(x: Int, y: Int): Int = grid(y)(x)

  def getCell(cell: Cell): Int = getCell(cell.x, cell.y)

  def isPopulated(cell: Cell): Boolean = getCell(cell) > 0

  def getColumn(x: Int): List[Int] = completed(Range(0, 9).map(getCell(x = x, _)).toList)

  def getBox(cell: Cell): List[Int] = {
      val (xTopLeft, yTopLeft) = ((cell.x / 3) * 3, (cell.y / 3) * 3)
      completed((
        for {
          y <- Range(yTopLeft, yTopLeft + 3)
          x <- Range(xTopLeft, xTopLeft + 3)
        } yield getCell(x, y)
      ).toList)
  }

  def isValid: Boolean = {
    !Range(0, 9).exists { i =>
      val row = getRow(i)
      val col = getColumn(i)
      val box = getBox(Cell((i / 3) * 3, (i % 3) * 3))
      
      row.distinct.length != row.length ||
        col.distinct.length != col.length ||
        box.distinct.length != box.length
    }
  }

  def render: String = {
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

  def solve: Option[Array[Array[Int]]] = {
    def candidates(cell: Cell): List[Int] = {
      val taken = (getRow(cell.y) ++ getColumn(cell.x) ++ getBox(cell)).toSet + getCell(cell)
      (1 to 9).filterNot(taken.contains).toList
    }

    // def displayCellDetails(x: Int, y: Int, candidates: List[Int]): Unit = {
    //   println(s"grid(x=$x, y=$y) = ${grid(y)(x)}")
    //   if (grid(y)(x) == 0) then {
    //     println(
    //       s"\n     sameRow = ${getRow(y).mkString("(", ", ", ")")}" +
    //       s"\n     sameCol = ${getColumn(x).mkString("(", ", ", ")")}" +
    //       s"\n     sameBox = ${getBox(y, x).mkString("(", ", ", ")")}" +
    //       s"  candidates = ${candidates.mkString("(", ", ", ")")}"
    //     )
    //   }
    // }

    def displayCellDetails(cell: Cell, candidates: List[Int]): Unit = {
      println(s"grid(x=${cell.x}, y=${cell.y}) = ${getCell(cell)}, candidates = ${candidates.mkString("(", ", ", ")")}")
    }

    println(Soduku(grid).render)

     if (isValid) {
      // solve it
      val cells = (
        for {
          y <- Range(0, 9)
          x <- Range(0, 9)
        } yield Cell(x, y)
      ).filterNot(isPopulated)

      cells.map { cell =>
        cell -> candidates(cell)
      }
      .foreach {
        case (cell, candidates) => displayCellDetails(cell, candidates)
      }
      None
      // NEXT:
      //  val toProcess = collection.mutable.ListBuffer(start -> List(start))
    } else {
      None
    }
  }
}
