package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{MapTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorFilter extends TestNumeric with TestArray {

  "Operator Filter f(el: Boolean => el)" should "return value" in {
    val tree = new ComposeLogic("filter", Array(
      ValueLogic(Some(arrBool), Some(arrBoolType)),
      ValueLogic(None, None, Some(""))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (arrBool.filter(_.booleanValue()))
  }

  "Operator Filter f(el: Float => el + 1 <= 0)" should "return value" in {
    val tree = new ComposeLogic("filter", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("<=", Array(
        new ComposeLogic("+", Array(
          ValueLogic(None, None, Some("")),
          ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.filter(_ + 1 <= 0)
  }


  "Operator Filter f(el: String => el.substring(string0))" should "return value" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("filter", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some("")),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME))),
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrString.filter(string0.contains)
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