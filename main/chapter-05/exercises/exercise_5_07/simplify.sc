// Exercise: Define a function that uses pattern matching on the Exprs we saw earlier
// to perform simple algebraic simplifications

import model.*

case class FinalBinOp(binOp: BinOp) extends Expr

def stringify(expr: Expr): String = expr match {
    case BinOp(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
    
    case FinalBinOp(binOp) => s"${stringify(binOp)}F"
    
    case Literal(value) => value.toString
    
    case Variable(name) => name
}

def simplify(expr: Expr): Expr = {
  def finalise(expr: Expr): Expr = expr match {
    case BinOp(left, op, right) => BinOp(finalise(left), op, finalise(right))
    case FinalBinOp(binOp) => binOp
    case x => x
  }

  def loop(expr: Expr): Expr = {
    expr match
      case BinOp(Literal(left), "+", Literal(right)) => Literal(left + right)
      case BinOp(Literal(left), "-", Literal(right)) => Literal(left - right)
      case BinOp(Literal(left), "*", Literal(right)) => Literal(left * right)

      // multiply by 1
      case BinOp(left, "*", Literal(1)) => left
      case BinOp(Literal(1), "*", right) => right

      // multiply by 0
      case BinOp(left, "*", Literal(0)) => Literal(0)
      case BinOp(Literal(0), "*", right) => Literal(0)

      // add 0
      case BinOp(left, "+", Literal(0)) => left
      case BinOp(Literal(0), "+", right) => right
      
      // subtract 0
      case BinOp(left, "-", Literal(0)) => left

      case BinOp(left @ BinOp(_, _, _), op, right) =>
        val simplified = loop(left)
        if (simplified == left) {
          // nowhere left to go
          loop(BinOp(FinalBinOp(left), op, right))
        } else {
          loop(BinOp(simplified, op, right))
        }

      case BinOp(left, op, right @ BinOp(_, _, _)) =>
        val simplified = loop(right)
        if (simplified == right) {
          // nowhere left to go
          loop(BinOp(left, op, FinalBinOp(right)))  
        } else {
          loop(BinOp(left, op, simplified))
        }

      case x => x
  }  
  finalise(loop(expr))
}