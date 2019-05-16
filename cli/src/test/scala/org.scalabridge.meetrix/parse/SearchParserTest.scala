package org.scalabridge.meetrix.parse

import org.joda.time.DateTime
import org.scalabridge.meetrix.error.ParseError
import org.scalabridge.meetrix.models.Category.Tech
import org.scalabridge.meetrix.models.Command.SearchEvents
import org.scalabridge.meetrix.models.City.London
import org.scalatest.{EitherValues, Matchers, WordSpec}

class SearchParserTest extends WordSpec with Matchers with EitherValues {

  val searchParser = SearchParser()

  "parseSearchArgs" should {

    "accept all valid combinations" in {
      val result = searchParser.parse(List("--city", "london", "--category", "tech", "--date", "11-01-2019"))
      result.right.value shouldBe SearchEvents(Some(London()), Some(Tech()), Some(new DateTime(2019, 1, 11, 0, 0)))
    }

    "accept just one filter" in {
      val result = searchParser.parse(List("--date", "11-01-2019"))
      result.right.value shouldBe SearchEvents(None, None, Some(new DateTime(2019, 1, 11, 0, 0)))
    }

    "fail if no filters provided" in {
      val result = searchParser.parse(List.empty).left.value
      result shouldBe ParseError("At least one filter required. The following are valid: category, city, date")
    }

    "fail if a key does not begin with --" in {
      val result = searchParser.parse(List("city", "london"))
      result.left.value shouldBe ParseError("Key must begin with --")
    }

    "fail if an odd number of args are provided" in {
      val result = searchParser.parse(List("--city", "london", "--category"))
      result.left.value shouldBe ParseError("Key of a config pair supplied without a value: --category")
    }

    "fail if an unknown key is provided" in {
      val result = searchParser.parse(List("--hyena", "london"))
      result.left.value shouldBe ParseError("Filter key not recognised: hyena. The following are valid: category, city, date")
    }

    "fail if a city cannot be parsed" in {
      val result = searchParser.parse(List("--city", "giraffe"))
      result.left.value shouldBe ParseError("giraffe not recognised as a City. The following are valid: london, berlin")
    }

    "fail if a category cannot be parsed" in {
      val result = searchParser.parse(List("--category", "chinchilla"))
      result.left.value shouldBe ParseError("chinchilla not recognised as a Category. The following are valid: tech, art")
    }
  }
}
