package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{MapTypeValue, OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOptionGetOrDefaultMap extends TestNumeric with TestMap {

  "Operator OptionGetOrDefaultMap Some(Map)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_map", Array(
      ValueLogic(Some(Some(mapInt)), Some(OptionTypeValue(mapIntType)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapInt
  }

  "Operator OptionGetOrDefaultMap Map" should "return value" in {
    val tree = new ComposeLogic("get_or_default_map", Array(
      ValueLogic(Some(mapInt), Some(mapIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapInt
  }

  "Operator OptionGetOrDefaultMap None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_map", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(mapIntType)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe Map[Any, Any]()
  }

  "Operator OptionGetOrDefaultMap option non map" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_map", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToMap can only be applied to Option[Map[_, _]] or Map[_, _] values. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultMap non option non map" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_map", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToMap can only be applied to Option[Map[_, _]] or Map[_, _] values. Input condition: 5"
  }

}
