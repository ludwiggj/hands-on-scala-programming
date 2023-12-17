class TrieSet() {
  class Node(
    var hasValue: Boolean = false,
    val children: collection.mutable.Map[Char, Node] = collection.mutable.Map()
  )

  val root = Node()

  def add(s: String): Unit = {
    var current = root
    for (c <- s) current = current.children.getOrElseUpdate(c, Node())
    current.hasValue = true
  }

  def contains(s: String): Boolean = {
    var current = Option(root)
    for (c <- s if current.nonEmpty) current = current.get.children.get(c)
    current.exists(_.hasValue)
  }

  def prefixesMatchingString(s: String): Set[String] = {
    var current = Option(root)
    val output = Set.newBuilder[Int]
    for ((c, i) <- s.zipWithIndex if current.nonEmpty) {
        if (current.get.hasValue) output += i
        current = current.get.children.get(c)
    }

    if (current.exists(_.hasValue)) output += s.length
    output.result().map(s.substring(0, _))
  }

  def stringsMatchingPrefix(s: String): Set[String] = {
    var current = Option(root)
    for (c <- s if current.nonEmpty) current = current.get.children.get(c)
    if (current.isEmpty) Set()
    else {
      val output = Set.newBuilder[String]
      def recurse(current: Node, path: List[Char]): Unit = {
        if (current.hasValue) output += (s + path.reverse.mkString)
        for ((c, n) <- current.children) recurse(n, c :: path)
      }
      recurse(current.get, Nil)
      output.result()
    }
  }
}