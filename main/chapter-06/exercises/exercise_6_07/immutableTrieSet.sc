  class ImmutableNode(
    val hasValue: Boolean = false,
    val children: Map[Char, ImmutableNode] = Map()
  ) {
    override def toString(): String = toStringIndent()

    def toStringIndent(indent: Int = 0): String = {
      val indentStr = " " * indent
      val fieldIndentStr = " " * (indent + 2)
      val mapIndentStr = " " * (indent + 4)
      val childrenStr = children.foldLeft("") { 
        case (acc, (ch, n)) => acc + s"\n$mapIndentStr$ch ->\n${n.toStringIndent(indent + 6)}"
      }
      val mapStr = if (childrenStr.isEmpty()) "Map()" else s"Map($childrenStr\n$fieldIndentStr)"
      s"${indentStr}ImmutableNode(\n${fieldIndentStr}hasValue=$hasValue,\n${fieldIndentStr}children=$mapStr\n${indentStr}),"
    }

    def add(s: String): ImmutableNode = {
      val ch = s.head
      val remainder = s.drop(1)

      children.get(ch) match {
        case Some(child) =>
          val updatedNode = if (remainder.isEmpty()) {
            ImmutableNode(
              hasValue = true,
              children = child.children
            )
          } else {
            child.add(remainder)
          }
          
          ImmutableNode(
            children = children.removed(ch) + (ch -> updatedNode)
          )

        case None =>
          val newNode = if (remainder.isEmpty()) {
            ImmutableNode(hasValue = true)
          } else {
            ImmutableNode().add(remainder)
          }
          
          ImmutableNode(
            children = children + (ch -> newNode)
          )             
      }
    }
  }

class ImmutableTrieSet(val root: ImmutableNode = ImmutableNode()) {
  override def toString(): String =
    s"ImmutableTrieSet(root=\n${root.toStringIndent(2)}\n)"

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
      def recurse(current: ImmutableNode, path: List[Char]): Unit = {
        if (current.hasValue) output += (s + path.reverse.mkString)
        for ((c, n) <- current.children) recurse(n, c :: path)
      }
      recurse(current.get, Nil)
      output.result()
    }
  }
}

object ImmutableTrieSet {
  def apply(strings: List[String]): ImmutableTrieSet = {
    val root = strings.foldLeft(ImmutableNode()) {
      case (n, s) => n.add(s)
    }
    new ImmutableTrieSet(root)
  }
}