import mill._, scalalib._

object foo extends ScalaModule {
  def scalaVersion = "3.3.1"

  object test extends ScalaTests {
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.11")
    def testFramework = "utest.runner.Framework"
  }
}
