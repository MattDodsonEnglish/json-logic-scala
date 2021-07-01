package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.JsNumber
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerFloat extends AnyFlatSpec with Matchers {

  "Marshall Float value" should "return value" in {
    MarshallerFloat.marshal(5f) shouldBe JsNumber(5f)
  }

  "Marshall java.lang.Float value" should "return value" in {
    MarshallerFloat.marshal(5f: java.lang.Float) shouldBe JsNumber(5f)
  }

  "Marshall non Float value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerFloat.marshal(null)}
    val expectedErrorMessage = "Illegal input argument to MarshallerFloat: null." +
      "\nMarshallerFloat can only be applied to Float values." +
      "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct."
    thrown.getMessage shouldBe expectedErrorMessage
  }
}