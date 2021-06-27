package com.github.celadari.jsonlogicscala.deserialize.defaults

import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsFalse, JsNumber, JsTrue}

class TestUnmarshallerByte extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Byte value" in {
    UnmarshallerByte.unmarshal(JsNumber(5.toByte)) shouldBe 5.toByte
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerByte.unmarshal(null)}
    val expectedErrorMessage = "Illegal input argument to UnmarshallerByte: null." +
      "\nUnmarshallerByte could not unmarshall to Byte value." +
      "\nCheck if \"type\" and \"var\" are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
