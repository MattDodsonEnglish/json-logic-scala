package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOrBitwise extends TestNumeric {


  "Operator OrBitwise Byte | Byte" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yByte)
  }

  "Operator OrBitwise Byte | Short" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yShort)
  }

  "Operator OrBitwise Byte | Int" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt | yInt)
  }

  "Operator OrBitwise Byte | Long" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yLong)
  }

  "Operator OrBitwise different types" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yByte | xLong | yInt | xShort | xShort)
  }

}