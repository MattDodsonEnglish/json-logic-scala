package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{MapTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic, VariableLogic}

class TestOperatorMap extends TestNumeric with TestArray {


  "Operator Map f(el: Float => el + 1)" should "return value" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("+", Array(
        ValueLogic(None, None, Some("")),
        ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.map(_ + xFloat)
  }

  "Operator Map f(el: Boolean => if (el) xFloat else xDouble)" should "return value" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrBool), Some(arrBoolType)),
      new ComposeLogic("if", Array(
        ValueLogic(None, None, Some("")),
        ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
        ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrBool.map(bool => if (bool) xFloat else yDouble)
  }


  "Operator Map f(el: String => map0(el))" should "return value" in {
    val map0 = Map("I" -> "You", "love" -> "prefer", "walking" -> "wandering", "in" -> "around", "New York" -> "Paris")
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("at", Array(
        ValueLogic(None, None, Some("")),
        ValueLogic(Some(map0), Some(MapTypeValue(SimpleTypeValue(STRING_CODENAME))))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrString.map(map0.apply)
  }

  "Operator Map f(el: Float => el**2 + el + 3)" should "return value" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrDouble), Some(arrDoubleType)),
      new ComposeLogic("+", Array(
        new ComposeLogic("**", Array(
          ValueLogic(None, None, Some("")),
          ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(None, None, Some("")),
        ValueLogic(Some(3), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrDouble.map(el => el*el + el + 3)
  }

}
