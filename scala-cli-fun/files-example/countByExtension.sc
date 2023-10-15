val (ext, directory) = args.toSeq match
  case Seq(ext) => (ext, os.pwd)
  case Seq(ext, directory) => (ext, os.Path(directory))
  case other =>
    println(s"Expected: `extension [directory]` but got: `${other.mkString(" ")}`")
    sys.exit(1)

val files = filesByExtension(ext, directory)
files.map(_.relativeTo(directory)).foreach(println)