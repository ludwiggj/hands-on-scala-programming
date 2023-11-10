def helloMessage(names: Seq[String]) = names match
  case Nil =>
    "Hello!"
  case names =>
    names.mkString("Hello: ", ", ", "!")

def main(args: Array[String]): Unit = {
  println(helloMessage(args.toSeq))
}
