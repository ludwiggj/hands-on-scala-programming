// Exercise: Define a function that uses pattern matching on the Exprs we saw earlier
// to perform simple algebraic simplifications

import model.*

def stringify(expr: Expr): String = expr match {
    case BinOp(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
    
    case Literal(value) => value.toString
    
    case Variable(name) => name
}

def simplify(expr: Expr): Expr = {
  expr match
    // literal operations  

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

    case BinOp(Literal(left), "+", Literal(right)) => Literal(left + right)
    case BinOp(Literal(left), "-", Literal(right)) => Literal(left - right)
    case BinOp(Literal(left), "*", Literal(right)) => Literal(left * right)

    // binary ops
    case BinOp(left @ BinOp(_, _, _), op, right @ BinOp(_, _, _)) =>
      val simplifiedLeft = simplify(left)
      val simplifiedRight = simplify(right)

      if ((simplifiedLeft != left) || (simplifiedRight != right)) {
        simplify(BinOp(simplifiedLeft, op, simplifiedRight))
      } else {
        BinOp(left, op, right)
      }
      

    case BinOp(left @ BinOp(_, _, _), op, right) =>
      val simplifiedLeft = simplify(left)

      if ((simplifiedLeft != left)) {
        simplify(BinOp(simplifiedLeft, op, right))
      } else {
        BinOp(left, op, right)
      }
    
    case BinOp(left, op, right @ BinOp(_, _, _)) =>
      val simplifiedRight = simplify(right)

      if (simplifiedRight != right) {
        simplify(BinOp(left, op, simplifiedRight))
      } else {
        BinOp(left, op, right)
      }

    // This covers:
    //   BinOp(Literal(_), _, Literal(_))
    //   BinOp(Variable(_), _, Variable(_))
    //   BinOp(Variable(_), _, Literal(_))
    //   BinOp(Literal(_), _, Variable(_))
    case b @ BinOp(_, _, _) => b

    case l @ Literal(_) => l

    case v @ Variable(_) => v
}
