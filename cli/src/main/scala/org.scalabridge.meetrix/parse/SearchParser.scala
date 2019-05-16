package org.scalabridge.meetrix.parse

import org.joda.time.format.DateTimeFormat
import org.scalabridge.meetrix.error.ParseError
import org.scalabridge.meetrix.models.Command.SearchEvents
import org.scalabridge.meetrix.models.{Category, City}

import scala.annotation.tailrec

case class SearchParser() {

  private val format = DateTimeFormat.forPattern("dd-MM-yyyy")

  def parse(args: List[String]): Either[ParseError, SearchEvents] =
    if (args.isEmpty) Left(ParseError(s"At least one filter required. The following are valid: category, city, date"))
    else helper(args, Right(SearchEvents()))

  @tailrec
  private def helper(args: List[String], acc: Either[ParseError, SearchEvents]): Either[ParseError, SearchEvents] =
    args match {
      case Nil                               => acc
      case head :: _ if head.take(2) != "--" => Left(ParseError("Key must begin with --"))
      case head :: Nil                       => Left(ParseError(s"Key of a config pair supplied without a value: $head"))
      case key :: value :: tail              => helper(tail, acc.flatMap(addFilter(_, key, value)))
    }

  // TODO: add error handling for date
  // TODO: pass in city/date/category parsers
  private def addFilter(searchEvents: SearchEvents, key: String, value: String): Either[ParseError, SearchEvents] =
    key.toLowerCase match {
      case "--category" => Category.parse(value).map(searchEvents.addCategory)
      case "--city"     => City.parse(value).map(searchEvents.addCity)
      case "--date"     => Right(searchEvents.addDate(format.parseDateTime(value)))
      case _ =>
        Left(ParseError(s"Filter key not recognised: ${key.drop(2)}. The following are valid: category, city, date"))
    }
}
