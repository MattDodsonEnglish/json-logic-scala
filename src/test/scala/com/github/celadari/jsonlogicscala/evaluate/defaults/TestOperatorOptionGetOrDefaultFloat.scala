package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluationException, IllegalInputException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOptionGetOrDefaultFloat extends TestNumeric with TestArray {

  "Operator OptionGetOrDefaultFloat Some(Float)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(Some(xFloat)), Some(OptionTypeValue(SimpleTypeValue(FLOAT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator OptionGetOrDefaultFloat Some(java.lang.Float)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(Some(xFloat: java.lang.Float)), Some(OptionTypeValue(SimpleTypeValue(FLOAT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator OptionGetOrDefaultFloat None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(FLOAT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0f
  }

  "Operator OptionGetOrDefaultFloat Float" should "return value" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator OptionGetOrDefaultFloat java.lang.Float" should "return value" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(xFloat: java.lang.Float), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator OptionGetOrDefaultFloat option non float" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluationException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToFloat can only be applied to Option[Float] or Float values. Input conditon: Some(5)"
  }

  "Operator OptionGetOrDefaultFloat non option non float" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_float", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluationException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToFloat can only be applied to Option[Float] or Float values. Input conditon: 5"
  }

}