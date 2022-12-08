package org.kcsup.aoc
package day5

import scala.collection.mutable
import scala.io.Source

/*
 [X] [X] [X]
  1   2   3

  Cargo at Incises: 1, 5, 9
  So: y = 4x - 3 (where y is the indicie and x is the column)
  And: (y + 3) / 4 = x
*/
@main def main(): Unit =
  val inputFile = System.getProperty("user.dir") + "/src/main/scala/day5/day5input.txt"
  val lines: Array[String] = Source.fromFile(inputFile).getLines().toArray
  val cargoLines = lines.slice(0, 8)
  val commandLines = lines.slice(10, lines.length)
  val omit = List(' ', '[', ']')
  val cargo: Array[mutable.Stack[Char]] = Array.fill(9){ mutable.Stack[Char]() }

  for(line <- cargoLines)
    for(i <- 0 until line.length)

      if(!omit.contains(line(i)))
        cargo(cargoColumn(i) - 1).append(line(i))
  println(cargo.mkString("Array(", ", ", ")"))

  for(line <- commandLines)
    /*
     0: Amount
     1: From
     2: To
    */
    val command = getCommands(line)

    // Day 1
//    0.until(command(0)).foreach(_ => {
//      cargo(command(2) - 1).push(cargo(command(1) - 1).pop())
//    })

    // Day 2
    val temp: mutable.Stack[Char] = mutable.Stack[Char]()
    0.until(command(0)).foreach(_ => {
      temp.push(cargo(command(1) - 1).pop())
    })
    temp.foreach(car => cargo(command(2) - 1).push(car))

  println(cargo.mkString("Array(", ", ", ")"))
  val stack = mutable.Stack("Among us")
  stack.push("e")
  println(stack)
  for(c <- cargo) print(c.head)


def cargoColumn(i: Int): Int =
  (i + 3) / 4

def getCommands(commandString: String): (Int, Int, Int) =
  val c = commandString.split(" ")
  (c(1).toInt, c(3).toInt, c(5).toInt)
