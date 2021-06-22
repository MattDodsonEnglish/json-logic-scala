package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorNeg extends TestBoolean with TestNumeric with TestArray {


  "Operator Neg true" should "return false" in {
    val tree = new ComposeLogic("!", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xBool
  }

  "Operator Neg false" should "return true" in {
    val tree = new ComposeLogic("!", Array(
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !yBool
  }

  "Operator Neg And true false" should "return true" in {
    val tree = new ComposeLogic("!", Array(
      new ComposeLogic("and", Array(
        ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
        ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !(xBool && yBool)
  }

  "Operator Neg Or true false" should "return false" in {
    val tree = new ComposeLogic("!", Array(
      new ComposeLogic("or", Array(
        ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
        ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !(xBool || yBool)
  }

}
