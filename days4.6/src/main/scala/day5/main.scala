package org.kcsup.aoc
package day5

import scala.collection.mutable


@main def main(): Unit =
  val list = mutable.Stack[String]()
  list.push("among us")
  list.foreach(i => println(i))
