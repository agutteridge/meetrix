package org.scalabridge.meetrix.parse

import org.scalabridge.meetrix.error.ParseError
import org.scalabridge.meetrix.models.Command

/** Functions which are used to parse user input (command line arguments) */
object InputParser {

  def parseInput(args: List[String], searchParser: SearchParser): Either[ParseError, Command] =
    args match {
      case Nil => Left(ParseError("Command not provided"))

      case firstArg :: others =>
        firstArg match {
          case "search" => searchParser.parse(others)
          case unrecognizedCmd =>
            Left(ParseError(s"Unrecognized command: $unrecognizedCmd. The following are valid: search"))
        }
    }
}
