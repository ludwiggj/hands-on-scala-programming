import scala.util.Failure
import scala.util.Success
// Exercise: Define a pair of methods withFileWriter and withFileReader that can be called as shown below.
// Each method should take the name of a file, and a function value that is called with a java.io.BufferedReader or
// java.io.BufferedWriter that it can use to read or write data. Opening and closing of the reader/writer should be
// automatic, such that a caller cannot forget to close the file. This is similar to Python "context managers" or Java
// "try-with-resource" syntax.

//withFileWriter("File.txt") { writer =>
//  writer.write("Hello\n"); writer.write("World!")
//}

//val result = withFileReader("File.txt") { reader =>
//  reader.readLine() + "\n" + reader.readLine()
//}

//assert(result == "Hello\nWorld!")

//</> 3.67.scala

//You can use the Java standard library APIs java.nio.file.Files.newBufferedWriter and newBufferedReader for workin
// with file readers and writers. We will get more familiar with working with files and the filesystem in Chapter
// 7: Files and Subprocesses.

import java.io.{BufferedWriter, BufferedReader}
import java.nio.file.{Files, Path}
import scala.util.{Try, Using}

def withFileWriter[T](fileName: String)(write: BufferedWriter => T): Try[T] = {
    // See https://www.scala-lang.org/api/2.13.6/scala/util/Using$.html
    Using(Files.newBufferedWriter(Path.of(fileName))) { writer =>
        write(writer)
    }
}

def withFileReader[T](fileName: String)(read: BufferedReader => T): Try[T] = {
    Using(Files.newBufferedReader(Path.of(fileName))) { reader =>
        read(reader)
    }
}

val fileName = "HelloDolly.txt"

withFileWriter(fileName) { writer =>
 writer.write("Hello\n"); writer.write("World!")
}

withFileReader(fileName) { reader =>
  reader.readLine() + "\n" + reader.readLine()
} match
    case Failure(exception) => println(s"Oh dear: ${exception.getMessage()}")
    case Success(content) => println(s"Hurrah! Read following from $fileName:\n$content")


/* Model answer...
def withFileWriter[T](fileName: String)(handler: java.io.BufferedWriter => T) = {
  val output = java.nio.file.Files.newBufferedWriter(java.nio.file.Paths.get(fileName))
  try handler(output)
  finally output.close()
}

def withFileReader[T](fileName: String)(handler: java.io.BufferedReader => T) = {
  val input = java.nio.file.Files.newBufferedReader(java.nio.file.Paths.get(fileName))
  try handler(input)
  finally input.close()
}
*/