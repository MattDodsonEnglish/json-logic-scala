package com.github.celadari.jsonlogicscala.evaluate

import com.github.celadari.jsonlogicscala.evaluate.CompositionOperator.ComposeJsonLogicCore
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}


object EvaluatorLogic {
  implicit val reduceLogic: EvaluatorLogic = new EvaluatorLogic

}

class EvaluatorLogic(implicit val conf: EvaluatorLogicConf) {

  protected[this] def reduceComposeLogic(condition: ComposeLogic, logicToValue: Map[ComposeLogic, Map[String, Any]]): Any = {
    val operator = condition.operator
    val confMethod = conf.operatorToMethodConf(operator)

    // Composition operators: map, filter, reduce
    if (confMethod.isCompositionOperator) {
      val arrValues = evaluate(condition.conditions(0), logicToValue).asInstanceOf[Array[Any]]
      val logicArr = condition.conditions.slice(1, condition.conditions.length)
      return confMethod.ownerMethodOpt.get.asInstanceOf[CompositionOperator].composeOperator(arrValues, logicArr, condition, this, logicToValue)
    }

    val conditionsEval = condition.conditions.map(cond => evaluate(cond, logicToValue))

    // Global operators: ifElse, merge, in
    if (!confMethod.isReduceType) {
      val methods = confMethod.ownerMethodOpt.get.getClass.getMethods.filter(_.getName == confMethod.methodName).toSet
      val (paramTypes, conditionsEvalCasted) = MethodSignatureFinder.maxMinsAndCastedValues(conditionsEval, methods.map(_.getParameterTypes.apply(0)))
      if (paramTypes.isEmpty) throw new Error(s"Method ${confMethod.methodName} doesn't accept parameter of type: ${conditionsEval.getClass}")

      val method = methods.find(_.getParameterTypes.apply(0) == paramTypes.head).get

      return method.invoke(confMethod.ownerMethodOpt.get, conditionsEvalCasted)
    }

    val methodsAndIsOwned = new MethodSignatureFinder(conditionsEval, confMethod).findPaths().head

    methodsAndIsOwned
      .zip(conditionsEval.tail)
      .foldLeft[Any](conditionsEval.head){case (conditionEval1, ((method, isMethodOwnedByReducedValue), conditionEval2)) => {
        if (isMethodOwnedByReducedValue) method.invoke(conditionEval1, conditionEval1, conditionEval2)
        else method.invoke(confMethod.ownerMethodOpt.get, conditionEval1, conditionEval2)
      }}
  }

  protected[this] def evaluateValueLogic(condition: ValueLogic[_]): Any = {
    val value = condition.valueOpt.get
    val typeValueOpt = condition.typeCodenameOpt
    if (typeValueOpt.isEmpty) return null

    val evaluatorClassOpt = conf.valueLogicTypeToReducer.get(typeValueOpt.get)
    evaluatorClassOpt
      .getOrElse(EvaluatorValueLogic.identityReducerValueLogic)
      .evaluateValueLogic(value)
  }

  def evaluate(condition: JsonLogicCore, logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]): Any = {
    condition match {
      case composeLogic: ComposeLogic => reduceComposeLogic(composeLogic, logicOperatorToValue)
      case valueLogic: ValueLogic[_] => evaluateValueLogic(valueLogic)
      case VariableLogic(variableName, composeOperator) => logicOperatorToValue(composeOperator)(variableName)
      case other => throw new IllegalArgumentException(s"Invalid argument: $other")
    }
  }

  def eval(jsonLogicCore: JsonLogicCore): Any = {
    val composedLogic = new ComposeJsonLogicCore(jsonLogicCore).evaluate()
    val logicOperatorToValue: Map[ComposeLogic, Map[String, Any]] = Map()
    evaluate(composedLogic, logicOperatorToValue)
  }

}
