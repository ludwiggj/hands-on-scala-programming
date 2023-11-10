package foo.exercises.chapter_04

import utest._

object Example04Tests extends TestSuite {
  def tests = Tests{
    test("hello"){
      val result = Example04.hello()
      assert(result == "Hello World")
      result
    }
  }
}
