package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorPow extends TestNumeric {


  "Operator Pow Byte ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xByte.toInt: java.lang.Integer, yByte.toInt: java.lang.Integer)
  }

  "Operator Pow Byte ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xByte.toInt: java.lang.Integer, yShort.toInt: java.lang.Integer)
  }

  "Operator Pow Byte ** Int" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xInt, yInt)
  }

  "Operator Pow Byte ** Long" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xByte.toLong, yLong)
  }

  "Operator Pow Byte ** Float" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xByte.toDouble, yFloat.toDouble)
  }

  "Operator Pow Byte ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xByte.toDouble, yDouble)
  }

  "Operator Pow different types" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe
      math.pow(math.pow(math.pow(math.pow(OperatorPow.pow(OperatorPow.pow(xByte.toInt: java.lang.Integer, yByte.toInt: java.lang.Integer).toLong, xLong).toDouble, xFloat.toDouble), yInt.toDouble), xShort.toDouble), xShort.toDouble)
  }

}
