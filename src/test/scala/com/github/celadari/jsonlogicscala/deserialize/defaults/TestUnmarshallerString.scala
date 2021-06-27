package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNull, JsString}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerString extends AnyFlatSpec with Matchers {

  "Unmarshall JsString value" should "return String value" in {
    UnmarshallerString.unmarshal(JsString("Walking in the rain")) shouldBe "Walking in the rain"
  }

  "Unmarshall non JsString value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerString.unmarshal(JsNull)}
    val expectedErrorMessage = "Illegal input argument to UnmarshallerString: null." +
      "\nUnmarshallerString could not unmarshall to String value." +
      "\nCheck if \"type\" and \"var\" are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
