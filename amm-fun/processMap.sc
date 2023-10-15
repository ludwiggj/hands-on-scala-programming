import $ivy.`org.typelevel::cats-core:2.9.0`
import cats.implicits.*

//https://stackoverflow.com/questions/53945996/cats-effecthow-to-transform-mapx-ioy-to-iomapx-y
val m = Map(
  "today" -> List(Right(1), Left("oh no"), Right(3), Left("yikes")),
  "tomorrow" -> List(Left("super"), Right(2), Left("smashing"), Right(4), Left("great")),
  "nextweek" -> List()
)
val map: Map[String, (List[String], List[Int])] = m.view.mapValues(_.separate).toMap
println(map)

// Map(
//   today -> (List(oh no, yikes),List(1, 3)),
//   tomorrow -> (List(super, smashing, great),List(2, 4)),
//   nextweek -> (List(),List())
// )

println(map.foldLeft(List.empty[String]){case (acc, (_, (errors, _))) => acc ::: errors})

// List(oh no, yikes, super, smashing, great)

println(map.view.mapValues{case (_, y) => y }.toMap)

// Map(today -> List(1, 3), tomorrow -> List(2, 4), nextweek -> List())