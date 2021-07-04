package com.github.celadari.jsonlogicscala.evaluate

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.github.celadari.jsonlogicscala.evaluate.impl.{EvaluatorValueLogicImplArrayInt, EvaluatorValueLogicImplDouble, EvaluatorValueLogicImplInt, EvaluatorValueLogicImplString, OperatorImplGreater, OperatorImplPrefix}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.{DOUBLE_CODENAME, INT_CODENAME, STRING_CODENAME}
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogicConf.DEFAULT_METHOD_CONFS
import com.github.celadari.jsonlogicscala.exceptions.ConfigurationException

class TestEvaluatorLogicConf extends AnyFlatSpec with Matchers {

  "createConf default path" should "return conf" in {
    val result = EvaluatorLogicConf.createConf()
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater), true, false, false),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), false, false, false)
      ),
      DEFAULT_METHOD_CONFS,
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map()
    )

    result shouldBe expectedResult
  }

  "createConf other path with manual add priority" should "return conf" in {
    val result = EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/normal/manual-add-priority/",
      evaluatorValueLogicManualAdd = Map(SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble),
      isPriorityToManualAdd = true
    )
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater), true, false, false),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), false, false, false)
      ),
      DEFAULT_METHOD_CONFS,
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map(
        SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble
      )
    )

    result shouldBe expectedResult
  }

  "createConf other path with meta-inf-priority" should "return conf" in {
    val result = EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/normal/meta-inf-priority/",
      evaluatorValueLogicManualAdd = Map(SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble),
      isPriorityToManualAdd = false
    )
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater), true, false, false),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), false, false, false)
      ),
      DEFAULT_METHOD_CONFS,
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map(
        SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble
      ),
      isPriorityToManualAdd = false
    )

    result shouldBe expectedResult
  }

  "createConf EvaluateValueLogic non EvaluateValueLogic object" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/cast-exception-singleton/"
    )}
    val expectedMessage = "Found object is not a 'com.github.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic' instance: \n'com.github.celadari.jsonlogicscala.deserialize.impl.UnmarshallerIntImpl$'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic non singleton with singleton true in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/non-singleton-exception-singleton-set-to-true/"
    )}
    val expectedMessage = "No singleton object found for: 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplInt'\nCheck if 'className' 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplInt' is correct and if 'singleton' property in 'int' property file is correct"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic non marshaller class" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/cast-exception-class/"
    )}
    val expectedMessage = "Found class is not a 'com.github.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic' instance: \n'com.github.celadari.jsonlogicscala.deserialize.impl.UnmarshallerDoubleImpl'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic singleton with singleton false in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/singleton-exception-singleton-set-to-false/"
    )}
    val expectedMessage = "No class found for: 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplDouble'\nCheck if 'className' 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplDouble' is correct and if 'singleton' property in 'double' property file is correct"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic className not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/classname-not-defined-exception-class/"
    )}
    val expectedMessage = "Property file 'double' must define key 'className'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic codename not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/codename-not-defined-exception-class/"
    )}
    val expectedMessage = "Property file 'int' must define key 'codename'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic wrong constructor argument names in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/wrong-constructor-argument-names-definition-exception-class/"
    )}
    val expectedMessage = "Field error, check that no field in 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplFloat' is missing in 'float' property file.\nCheck that no property in 'float' file is not undefined in 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplFloat' class.\nCheck if 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplFloat' class constructor requires arguments or if argument names defined in 'float' property file are correct"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic wrong property type in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/wrong-property-type-exception-class/"
    )}
    val expectedMessage = "Property 'singleton' in property file 'string' is not a valid boolean parameter"
    thrown.getMessage shouldBe expectedMessage
  }

  /*
  "Evaluate Value Logic with custom ValueLogicReducer" should "return value" in {
    val customIntValueLogicReducer = new EvaluatorValueLogic {
      override def evaluateValueLogic(value: Any): Any = "Custom EvaluatorValueLogic"
    }

    implicit val conf: EvaluatorLogicConf = EvaluatorLogicConf.createConf(
      evaluatorValueLogicManualAdd = Map(SimpleTypeValue("customInt") -> customIntValueLogicReducer)
    )
    val evaluator = new EvaluatorLogic
    val result1 = evaluator invokePrivate evaluateValueLogic(ValueLogic(Some(4), Some(SimpleTypeValue("customInt"))))
    val result2 = evaluator invokePrivate evaluateValueLogic(ValueLogic(Some(2), None))
    result1 shouldBe "Custom EvaluatorValueLogic"
    result2 shouldBe (null: Any)
  }

  "Evaluate Compose Logic reduceType default operators" should "return value" in {
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("+", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val result = evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())
    result shouldBe 8
  }

  "Evaluate Compose Logic non-defined operator" should "throw exception" in {
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("non-defined", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    an[IllegalInputException] shouldBe thrownBy {evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())}
  }

  "Evaluate Compose Logic non-existing method of nonReduce type operator" should "throw exception" in {
    object FooOperator extends Operator
    implicit val conf: EvaluatorLogicConf = EvaluatorLogicConf.createConf(
      operatorsManualAdd = Map("foo" -> MethodConf("foo", "nonexisting", Some(FooOperator), isReduceTypeOperator = false)) ++ EvaluatorLogicConf.DEFAULT_METHOD_CONFS
    )
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("foo", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    an[IncompatibleMethodsException] shouldBe thrownBy {evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())}
  }

  "Evaluate Compose Logic non-existing method of reduce type operator" should "throw exception" in {
    object FooOperator extends Operator
    implicit val conf: EvaluatorLogicConf = EvaluatorLogicConf.createConf(
      operatorsManualAdd = Map("foo" -> MethodConf("foo", "nonexisting", Some(FooOperator))) ++ EvaluatorLogicConf.DEFAULT_METHOD_CONFS
    )
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("foo", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    an[IncompatibleMethodsException] shouldBe thrownBy {evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())}
  }
*/
}
