package org.scalabridge.meetrix

import org.scalabridge.meetrix.parse.{InputParser, SearchParser}

object Main {

  def main(argsArgs: Array[String]): Unit = {

    val args = argsArgs.toList
    val searchParser = SearchParser()

    println(InputParser.parseInput(args, searchParser))
  }
}
