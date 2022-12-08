package org.kcsup.aoc
package day6

import scala.io.Source
import scala.util.control.Breaks._


@main def main(): Unit =
  println()
  val inputFile = System.getProperty("user.dir") + "/src/main/scala/day6/day6input.txt"
  val input: String = Source.fromFile(inputFile).getLines().toArray.head
  breakable {
    for (c <- 0 until input.length - 4)
      if (!hasDupe(input.substring(c, c + 4)))
        println(c + 4)
        break
  }
  
  breakable {
    for (m <- 0 until input.length - 14)
      if (!hasDupe(input.substring(m, m + 14)))
        println(m + 14)
        break
  }

def hasDupe(s: String): Boolean =
  for(c <- s) if(s.indexOf(c) != s.lastIndexOf(c)) return true

  false
