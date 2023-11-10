def filesByExtension(
  extension: String,
  dir: os.Path = os.pwd
): Seq[os.Path] =
    os.walk(dir).filter { f =>
      f.last.endsWith(s".$extension") && os.isFile(f)
    }

@main
def hello() = {
  println("Hello, world")
  println(os.pwd)
  println(filesByExtension("scala"))
}