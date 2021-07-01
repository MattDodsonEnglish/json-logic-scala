package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluationException, IllegalInputException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOptionGetOrDefaultString extends TestNumeric with TestString {

  "Operator OptionGetOrDefaultString Some(String)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(Some(xString)), Some(OptionTypeValue(SimpleTypeValue(STRING_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xString
  }

  "Operator OptionGetOrDefaultString Some(java.lang.String)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(Some(xString: java.lang.String)), Some(OptionTypeValue(SimpleTypeValue(STRING_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xString
  }

  "Operator OptionGetOrDefaultString None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(STRING_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe ""
  }

  "Operator OptionGetOrDefaultString String" should "return value" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(xString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xString
  }

  "Operator OptionGetOrDefaultString java.lang.String" should "return value" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(xString: java.lang.String), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xString
  }

  "Operator OptionGetOrDefaultString option non string" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluationException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToString can only be applied to Option[String] or String values. Input conditon: Some(5)"
  }

  "Operator OptionGetOrDefaultString non option non string" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_string", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluationException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToString can only be applied to Option[String] or String values. Input conditon: 5"
  }

}