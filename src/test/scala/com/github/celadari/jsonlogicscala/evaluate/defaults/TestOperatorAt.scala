package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{MapTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorAt extends TestMap with TestNumeric with TestArray {


  "Operator At arrInt" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt(2)
  }

  "Operator At arrInt with Double Index" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      new ComposeLogic("+", Array(
        ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME))),
        ValueLogic(Some(2.0d), Some(SimpleTypeValue(DOUBLE_CODENAME)))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt((1 + 2.0d).toInt)
  }

  "Operator At mapInt Index" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some("plane"), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(mapInt), Some(mapIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapInt("plane")
  }

  "Operator At At mapMapLong" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some("boat"), Some(SimpleTypeValue(STRING_CODENAME))),
      new ComposeLogic("at", Array(
        ValueLogic(Some("navy"), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(Some(mapMapLong), Some(mapMapLongType))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapMapLong("navy")("boat")
  }

  "Operator At At mapArrDouble" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME))),
      new ComposeLogic("at", Array(
        ValueLogic(Some("plane"), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(Some(mapArrDouble), Some(mapMapLongType))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapArrDouble("plane")(1)
  }

}
