def getDayMonthYear(s: String) = s match {
    case s"$day-$month-$year" => println(s"found day: $day, month: $month, year: $year")
    case _ => println(s"$s is not a date")
}

getDayMonthYear("9-8-1965")
getDayMonthYear("9-8")

sealed trait Expr
case class BinOp(left: Expr, op: String, right: Expr) extends Expr
case class Literal(value: Int) extends Expr
case class Variable(name: String) extends Expr

val x_plus_1 = BinOp(Variable("x"), "+", Literal(1))
val x_times_y_minus_1 = BinOp(Variable("x"), "*", BinOp(Variable("y"), "-", Literal(1)))
val x_plus_1_times_y_minus_1 = BinOp(BinOp(Variable("x"), "+", Literal(1)), "*",  BinOp(Variable("y"), "-", Literal(1)))

def stringify(expr: Expr): String = expr match {
    case BinOp(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
    case Literal(value) => value.toString
    case Variable(name) => name
}

def evaluate(expr: Expr, values: Map[String, Int]): Int = expr match {
    case BinOp(left, "+", right) => evaluate(left, values) + evaluate(right, values)
    case BinOp(left, "-", right) => evaluate(left, values) - evaluate(right, values)
    case BinOp(left, "*", right) => evaluate(left, values) * evaluate(right, values)
    case BinOp(_, op, _) => throw new IllegalArgumentException(s"Unknown operator $op")
    case Literal(value) => value
    case Variable(name) => values(name)
}

val variables = Map("x" -> 10, "y" -> 20)

println(s"Variables: $variables")
println(s"${stringify(x_plus_1)} = ${evaluate(x_plus_1, variables)}")
println(s"${stringify(x_times_y_minus_1)} = ${evaluate(x_times_y_minus_1, variables)}")
println(s"${stringify(x_plus_1_times_y_minus_1)} = ${evaluate(x_plus_1_times_y_minus_1, variables)}")