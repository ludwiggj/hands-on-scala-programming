import java.time.LocalDate
import cats.Foldable
import cats.implicits.*
import cats.effect.IO
import cats.syntax.all.*
import cats.effect.unsafe.implicits.global

def getBreaks(date: LocalDate, junctionId: Int): IO[List[String]] =
    IO(List(s"Date: $date, junctionId: $junctionId", "Result"))

IO(
    Map(
      LocalDate.parse("2023-10-11") -> List(Right(1), Left("oh no"), Right(3), Left("yikes")),
      LocalDate.parse("2023-10-12") -> List(Left("super"), Right(2), Left("smashing"), Right(4), Left("great")),
      LocalDate.parse("2023-10-13") -> List()
    )
    .view
    .mapValues(_.separate)
    .toMap
).flatTap(m =>
    IO.println(m.values.map(_._1).flatten) >>
    IO.println(m.values.map(_._1).flatten.toList.combineAll)
).map(m =>
  m.map {
    case (date, (_, junctionIds)) =>
        (date -> junctionIds.map(id => getBreaks(date, id)).flatSequence)
  }.parUnorderedSequence
)
.flatten
.flatTap( m =>
  IO.println(m)
)
.unsafeRunSync()