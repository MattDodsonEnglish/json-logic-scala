package com.github.celadari.jsonlogicscala.evaluate

import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplSignature
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue

class TestMethodSignature extends AnyFlatSpec with Matchers {

  "Compose Logic evaluate with overloading methods" should "pick the right set of methods" in {
    val composeLogic = new ComposeLogic("method_signature", Array(
      ValueLogic(Some(new OperatorImplSignature.GrandChild()), Some(SimpleTypeValue("impl_signature")), None, Some("data1")),
      ValueLogic(Some(new OperatorImplSignature.GrandChild()), Some(SimpleTypeValue("impl_signature")), None, Some("data2")),
      ValueLogic(Some(new OperatorImplSignature.Child1{ num = 0 }), Some(SimpleTypeValue("impl_signature")), None, Some("data3")),
      ValueLogic(Some(new OperatorImplSignature.Child1{ num = 0 }), Some(SimpleTypeValue("impl_signature")), None, Some("data4"))
    ))
    val conf = EvaluatorLogicConf.createConf(methodConfsManualAdd=Map("method_signature" -> MethodConf(
      "method_signature",
      "doOperation",
      Some(OperatorImplSignature),
      isReduceTypeOperator = true,
      isCompositionOperator = false,
      isUnaryOperator = false
    )))
    val evaluatorLogic = new EvaluatorLogic()(conf)

    val result = evaluatorLogic.eval(composeLogic).asInstanceOf[OperatorImplSignature.Child1]
    result.num shouldBe 145
  }

}
