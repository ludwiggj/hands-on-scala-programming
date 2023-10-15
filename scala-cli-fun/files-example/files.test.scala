class TestSuite extends munit.FunSuite {
  test("hello") {
    val wd = os.home / "GJALWorkspace" / "hands-on-scala-programming" / "scala-cli-fun" / "files-example"
    val expected = Set("files.scala", "files.test.scala")
    println(filesByExtension("scala", wd))
    println(filesByExtension("scala", wd).map(_.last))
    val obtained = filesByExtension("scala", wd).map(_.last).toSet
    assertEquals(obtained, expected)
  }
}