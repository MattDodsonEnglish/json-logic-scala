package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{MapTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorReduce extends TestNumeric with TestArray {


  "Operator Reduce (acc, el) => 5 * acc + el" should "return value" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("+", Array(
        new ComposeLogic("*", Array(
          ValueLogic(None, None, Some("accumulator")),
          ValueLogic(Some(5), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(None, None, Some("current"))
      )),
      ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.foldLeft[Int](0)(5 * _ + _)
  }

  "Operator Reduce (acc, el) => if (el <= 0) 2 * acc + el else 5 * acc + 3" should "return value" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("if", Array(
        new ComposeLogic("<=", Array(
          ValueLogic(None, None, Some("current")),
          ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)), None)
        )),
        new ComposeLogic("+", Array(
          new ComposeLogic("*", Array(
            ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME))),
            ValueLogic(None, None, Some("accumulator"))
          )),
          ValueLogic(None, None, Some("current")),
        )),
        new ComposeLogic("+", Array(
          new ComposeLogic("*", Array(
            ValueLogic(Some(5), Some(SimpleTypeValue(INT_CODENAME))),
            ValueLogic(None, None, Some("accumulator"))
          )),
          ValueLogic(Some(3), Some(SimpleTypeValue(INT_CODENAME))),
        ))
      )),
      ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.foldLeft[Int](0){case (acc, el) => if (el <= 0) 2 * acc + el else 5 * acc + 3}
  }

  "Operator Reduce String (acc, el) => acc + el" should "return value" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("cat", Array(
        ValueLogic(None, None, Some("accumulator")),
        ValueLogic(Some(" "), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(None, None, Some("current"))
      )),
      ValueLogic(Some(""), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrString.foldLeft[String]("")(_ + " " + _)
  }

}