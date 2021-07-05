package com.github.celadari.jsonlogicscala.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.TreeException
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, SimpleTypeValue}

class TestValueLogic extends AnyFlatSpec with Matchers {

  "Compare same data ValueLogic" should "return true" in {
    val valueLogic1 = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data"))
    val valueLogic2 = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data"))
    valueLogic1 shouldBe valueLogic2
  }

  "Compare same variable ValueLogic" should "return true" in {
    val valueLogic1 = ValueLogic(Some(""), Some(AnyTypeValue), Some("variableName"), None)
    val valueLogic2 = ValueLogic(Some(""), Some(AnyTypeValue), Some("variableName"), None)
    valueLogic1 shouldBe valueLogic2
  }

  "Instantiate ValueLogic as both data and variable" should "thrown an exception" in {
    val thrown = the[TreeException] thrownBy {ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)), Some("variableName"), Some("data"))}
    println(thrown.getMessage)
    val expectedMessage = """ValueLogic cannot be variable compose and data at the same time: variableNameOpt and pathNameOpt cannot be both defined or empty
                            |ValueLogic(Some(4),Some(SimpleTypeValue(int)),Some(variableName),Some(data))""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

}
