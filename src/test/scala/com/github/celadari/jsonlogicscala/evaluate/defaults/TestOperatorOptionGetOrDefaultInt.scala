package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluationException, IllegalInputException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOptionGetOrDefaultInt extends TestNumeric with TestArray {


  "Operator OptionGetOrDefaultInt Some(Int)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(Some(xInt)), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt Some(java.lang.Integer)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(Some(xInt: java.lang.Integer)), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0
  }

  "Operator OptionGetOrDefaultInt Int" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt java.lang.Integer" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(xInt: java.lang.Integer), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt option non integer" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluationException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToInt can only be applied to Option[Int] or Int values. Input conditon: Some(5)"
  }

  "Operator OptionGetOrDefaultInt non option non integer" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluationException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToInt can only be applied to Option[Int] or Int values. Input conditon: 5"
  }

}