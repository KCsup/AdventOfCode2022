package org.kcsup.aoc
package day4

import scala.io.Source

@main def main(): Unit =
  val inputFile = System.getProperty("user.dir") + "/src/main/scala/day4/day4input.txt"
  val lines: Array[String] = Source.fromFile(inputFile).getLines().toArray

  var count = 0
  var atAll = 0
  for(line <- lines) {
    val bounds = line.split(",")


    val inner = bounds(0).split("-")
    val outer = bounds(1).split("-")

    if(contains(inner(0), inner(1), outer(0), outer(1))) count += 1

    if(containsAtAll(inner(0).toInt, inner(1).toInt, outer(0).toInt, outer(1).toInt)) atAll += 1
  }
  println(count)
  print(atAll)

def contains(l1: Int, u1: Int, l2: Int, u2: Int): Boolean =
  (l1 <= l2 && u1 >= u2) || (l2 <= l1 && u2 >= u1)

def containsAtAll(l1: Int, u1: Int, l2: Int, u2: Int): Boolean =
  for(i <- l1 to u1)
    for(j <- l2 to u2) if(i == j) return true
  false

def contains(l1: String, u1: String, l2: String, u2: String): Boolean =
  contains(l1.toInt, u1.toInt, l2.toInt, u2.toInt)
