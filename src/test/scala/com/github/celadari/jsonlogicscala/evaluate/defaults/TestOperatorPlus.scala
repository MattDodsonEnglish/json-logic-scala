package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorPlus extends TestNumeric {


  "Operator Plus Byte + Byte" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val reducer = new EvaluatorLogic
    println(reducer.evaluate(tree))
    reducer.evaluate(tree) shouldBe (xByte + yByte)
  }

  "Operator Plus Byte + Short" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val reducer = new EvaluatorLogic
    reducer.evaluate(tree) shouldBe (xByte + yShort)
  }

  "Operator Plus Byte + Int" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val reducer = new EvaluatorLogic
    reducer.evaluate(tree) shouldBe (xInt + yInt)
  }

  "Operator Plus Byte + Long" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val reducer = new EvaluatorLogic
    reducer.evaluate(tree) shouldBe (xByte + yLong)
  }

  "Operator Plus Byte + Float" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val reducer = new EvaluatorLogic
    reducer.evaluate(tree) shouldBe (xByte + yFloat)
  }

  "Operator Plus Byte + Double" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val reducer = new EvaluatorLogic
    reducer.evaluate(tree) shouldBe (xByte + yDouble)
  }

}
