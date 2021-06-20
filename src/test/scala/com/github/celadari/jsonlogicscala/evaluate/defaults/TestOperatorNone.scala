package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorNone extends TestNumeric with TestArray {

  "Operator None f(el: Boolean => el)" should "return value" in {
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrBool), Some(arrBoolType)),
      ValueLogic(None, None, Some(""))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (!arrBool.exists(_.booleanValue()))
  }

  "Operator None f(el: Float => el + 1 <= 0)" should "return value" in {
    val tree = new ComposeLogic("none", Array(
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
    evaluator.eval(tree) shouldBe !arrInt.exists(_ + 1 <= 0)
  }


  "Operator None f(el: String => el.substring(string0))" should "return value" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some("")),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME))),
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !arrString.exists(string0.contains)
  }

  "Operator None f(el: Float => el**2 + el + 3 == 4)" should "return value" in {
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrDouble), Some(arrDoubleType)),
      new ComposeLogic("==", Array(
        new ComposeLogic("+", Array(
          new ComposeLogic("**", Array(
            ValueLogic(None, None, Some("")),
            ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
          )),
          ValueLogic(None, None, Some("")),
          ValueLogic(Some(3), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !arrDouble.exists(el => el*el + el + 3 == 4)
  }

}