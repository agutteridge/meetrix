package org.scalabridge.meetrix.models

import org.scalabridge.meetrix.error.ParseError

sealed trait Category

object Category {

  final case class Tech() extends Category

  final case class Art() extends Category

  def parse(value: String): Either[ParseError, Category] = value.toLowerCase match {
    case "tech" => Right(Tech())
    case "art"  => Right(Art())
    case _      => Left(ParseError(s"$value not recognised as a Category. The following are valid: tech, art"))
  }
}
