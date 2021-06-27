package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsFalse, JsTrue}
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException

class TestUnmarshallerBoolean extends AnyFlatSpec with Matchers {

  "Unmarshall JsTrue value" should "return value" in {
    UnmarshallerBoolean.unmarshal(JsTrue) shouldBe true
  }

  "Unmarshall JsFalse value" should "return value" in {
    UnmarshallerBoolean.unmarshal(JsFalse) shouldBe false
  }

  "Unmarshall non JsBool value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerBoolean.unmarshal(null)}
    val expectedErrorMessage = "Illegal input argument to UnmarshallerBoolean: null." +
      "\nUnmarshallerBoolean could not unmarshall to boolean value." +
      "\nCheck if \"type\" and \"var\" are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
