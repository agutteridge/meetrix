package org.scalabridge.meetrix.models

import org.joda.time.DateTime

sealed trait Command

object Command {

  final case class ListEvents(location: Option[LatLng], radius: Option[Int]) extends Command

  final case class SearchEvents(
    maybeCity: Option[City]         = None,
    maybeCategory: Option[Category] = None,
    maybeDate: Option[DateTime]     = None
  ) extends Command {

    def addCategory(category: Category): SearchEvents = this.copy(maybeCategory = Some(category))

    def addCity(city: City): SearchEvents = this.copy(maybeCity = Some(city))

    def addDate(date: DateTime): SearchEvents = this.copy(maybeDate = Some(date))
  }
}
