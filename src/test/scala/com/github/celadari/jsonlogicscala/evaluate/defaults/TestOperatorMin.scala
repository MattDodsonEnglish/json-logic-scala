package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorMin extends TestNumeric {


  "Operator Min Byte Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xByte
    result.isInstanceOf[Byte] shouldBe true
  }

  "Operator Min Byte Short" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xShort
    result.isInstanceOf[Short] shouldBe true
  }

  "Operator Min Byte Int" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xInt
    result.isInstanceOf[Int] shouldBe true
  }

  "Operator Min Byte Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xLong
    result.isInstanceOf[Long] shouldBe true
  }

  "Operator Min Byte Float" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xFloat
    result.isInstanceOf[Float] shouldBe true
  }

  "Operator Min Byte Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xDouble
    result.isInstanceOf[Double] shouldBe true
  }

  "Operator Min different types" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xFloat
    result.isInstanceOf[Float] shouldBe true
  }

}
