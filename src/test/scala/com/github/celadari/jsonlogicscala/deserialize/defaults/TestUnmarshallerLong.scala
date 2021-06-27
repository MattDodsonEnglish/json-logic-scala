package com.github.celadari.jsonlogicscala.deserialize.defaults

import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsNumber

class TestUnmarshallerLong extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Long value" in {
    UnmarshallerLong.unmarshal(JsNumber(5L)) shouldBe 5L
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerLong.unmarshal(null)}
    val expectedErrorMessage = "Illegal input argument to UnmarshallerLong: null." +
      "\nUnmarshallerLong could not unmarshall to Long value." +
      "\nCheck if \"type\" and \"var\" are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
