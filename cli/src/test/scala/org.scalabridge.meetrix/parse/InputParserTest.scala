package org.scalabridge.meetrix.parse

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalabridge.meetrix.error.ParseError
import org.scalabridge.meetrix.models.Command.SearchEvents
import org.scalatest.{EitherValues, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar

class InputParserTest extends WordSpec with Matchers with MockitoSugar with EitherValues {

  private val searchParser = mock[SearchParser]

  when(searchParser.parse(any())).thenReturn(Right(SearchEvents()))

  "parseInput" should {
    "parse search command" in {
      InputParser.parseInput(List("search"), searchParser).right.value shouldBe SearchEvents()
    }

    "fail if no command is provided" in {
      InputParser.parseInput(List.empty, searchParser).left.value shouldBe ParseError("Command not provided")
    }

    "fail if the command is not recognised" in {
      val error = InputParser.parseInput(List("platypus"), searchParser).left.value
      error shouldBe ParseError("Unrecognized command: platypus. The following are valid: search")
    }
  }
}
