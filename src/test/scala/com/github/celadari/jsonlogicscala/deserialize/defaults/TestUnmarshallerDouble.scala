package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNull, JsNumber}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerDouble extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Double value" in {
    UnmarshallerDouble.unmarshal(JsNumber(5d)) shouldBe 5d
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerDouble.unmarshal(JsNull)}
    val expectedErrorMessage = "Illegal input argument to UnmarshallerDouble: null." +
      "\nUnmarshallerDouble could not unmarshall to Double value." +
      "\nCheck if \"type\" and \"var\" are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
