package com.github.celadari.jsonlogicscala.deserialize.defaults

import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsNumber

class TestUnmarshallerShort extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Short value" in {
    UnmarshallerShort.unmarshal(JsNumber(5.toShort)) shouldBe 5.toShort
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerShort.unmarshal(null)}
    val expectedErrorMessage = "Illegal input argument to UnmarshallerShort: null." +
      "\nUnmarshallerShort could not unmarshall to Short value." +
      "\nCheck if \"type\" and \"var\" are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
