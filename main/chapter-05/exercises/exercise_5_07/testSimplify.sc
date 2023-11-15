import org.scalatest.prop.TableDrivenPropertyChecks.*

import model.*

// Tests
val equations =
  Table(
    ("equation", "expected"),
    // (1 + 1) => 2
    (
      BinOp(Literal(1), "+", Literal(1)),
      Literal(2)
    ),
    // ((1 - 1) * x) => 0
    (
      BinOp(
        BinOp(Literal(1), "-", Literal(1)),
        "*",
        Variable("x")
      ),
      Literal(0)
    ),
    // (x * (-2 + 2)) => 0
    (
      BinOp(
        Variable("x"),
        "*",
        BinOp(Literal(-2), "+", Literal(2))
      ),
      Literal(0)
    ),
    // ((2 - 1) * x) => x
    (
      BinOp(
        BinOp(Literal(2), "-", Literal(1)),
        "*",
        Variable("x")
      ),
      Variable("x")
    ),
    // (x * (1 * 1)) => x
    (
      BinOp(
        Variable("x"),
        "*",
        BinOp(Literal(1), "*", Literal(1))
      ),
      Variable("x")
    ),
    // (x + 0) => x
    (
      BinOp(Variable("x"), "+", Literal(0)),
      Variable("x")
    ),
    // (0 + x) => x
    (
      BinOp(Literal(0), "+", Variable("x")),
      Variable("x")
    ),
    // (x - 0) => x
    (
      BinOp(Variable("x"), "-", Literal(0)),
      Variable("x")
    ),
    // (0 - x) => (0 - x)
    (
      BinOp(Literal(0), "-", Variable("x")),
      BinOp(Literal(0), "-", Variable("x"))
    ),
    // ((1 + 1) * x) => (2 * x)
    (
      BinOp(
        BinOp(Literal(1), "+", Literal(1)),
        "*",
        Variable("x")
      ),
      BinOp(Literal(2), "*", Variable("x"))
    ),
    // ((x + 1) * 6) => (x + 1) * 6
    (
      BinOp(
        BinOp(Variable("x"), "+", Literal(1)),
        "*",
        Literal(6)
      ),
      BinOp(
        BinOp(Variable("x"), "+", Literal(1)),
        "*",
        Literal(6)
      )
    ),
    // (6 * (x + 1)) => 6 * (x + 1)
    (
      BinOp(
        Literal(6),
        "*",
        BinOp(Variable("x"), "+", Literal(1))
      ),
      BinOp(
        Literal(6),
        "*",
        BinOp(Variable("x"), "+", Literal(1))
      )
    ),
    // ((x + 10) * (x + 20)) => ((x + 10) * (x + 20))
    (
      BinOp(
        BinOp(Variable("x"), "+", Literal(10)),
        "*",
        BinOp(Variable("x"), "+", Literal(20))
      ),
      BinOp(
        BinOp(Variable("x"), "+", Literal(10)),
        "*",
        BinOp(Variable("x"), "+", Literal(20))
      )
    ),
    // ((1 + 1) * y) + ((1 - 1) * x) => 2 * y
    (
      BinOp(
        BinOp(
          BinOp(Literal(1), "+", Literal(1)),
          "*",
          Variable("y")
        ),
        "+",
        BinOp(
          BinOp(Literal(1), "-", Literal(1)),
          "*",
          Variable("x")
        )
      ),
      BinOp(Literal(2), "*", Variable("y"))
    ),
    // (2 * y) + ((1 - 1) * x) => 2 * y
    (
      BinOp(
        BinOp(Literal(2), "*", Variable("y")),
        "+",
        BinOp(
          BinOp(Literal(1), "-", Literal(1)),
          "*",
          Variable("x")
        )
      ),
      BinOp(Literal(2), "*", Variable("y"))
    )
  )

def runTests(stringify: Expr => String, simplify: Expr => Expr): Unit = {
  forAll (equations) { (equation: Expr, expected: Expr) =>
    val equationStr = stringify(equation)
    val expectedStr = stringify(expected)
    val simplified = simplify(equation)
    assert(
      simplified == expected,
      s"Simplify: $equationStr, Expected: $expectedStr, Got: ${stringify(simplified)}"
    )
    println(s"Asserted: $equationStr => $expectedStr\n")
  }
}

object TestSimplify {
  import simplify.*
  def run: Unit = runTests(stringify, simplify)
}

object TestSimplifyTake2 {
  import simplifyTake2.*
  def run: Unit = runTests(stringify, simplify)
}

println("Testing Simplify...\n")
TestSimplify.run
println("Testing Simplify Take 2...\n")
TestSimplifyTake2.run