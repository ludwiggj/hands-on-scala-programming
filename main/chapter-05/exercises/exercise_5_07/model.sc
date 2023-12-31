// Not sealed so we can add in FinalOp in simplify.sc (take 1)

trait Expr

case class BinOp(left: Expr, op: String, right: Expr) extends Expr

case class Literal(value: Int) extends Expr

case class Variable(name: String) extends Expr