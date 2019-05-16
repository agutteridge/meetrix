package org.scalabridge.meetrix.models

import org.scalabridge.meetrix.error.ParseError

sealed trait City

object City {

  final case class London() extends City
  final case class Berlin() extends City

  def parse(value: String): Either[ParseError, City] = value.toLowerCase match {
    case "london" => Right(London())
    case "berlin" => Right(Berlin())
    case _        => Left(ParseError(s"$value not recognised as a City. The following are valid: london, berlin"))
  }

}
