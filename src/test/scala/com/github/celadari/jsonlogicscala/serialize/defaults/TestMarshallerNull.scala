package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.JsNull
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerNull extends AnyFlatSpec with Matchers {

  "Marshall null value" should "return value" in {
    MarshallerNull.marshal(null) shouldBe JsNull
  }

  "Marshall non null value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerNull.marshal(5)}
    val expectedErrorMessage = "Illegal input argument to MarshallerNull: 5." +
      "\nMarshallerNull can only be applied to null value." +
      "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
