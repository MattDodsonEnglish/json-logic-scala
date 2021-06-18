package com.github.celadari.jsonlogicscala.reduce

import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}


object ReduceLogic {
  implicit val reduceLogic: ReduceLogic = new ReduceLogic

}


class ReduceLogic(implicit val conf: ReduceLogicConf) {

  def reduceComposeLogic(condition: ComposeLogic): Any = {
    val operator = condition.operator
    val confMethod = conf.operatorToMethodConf(operator)

    // Composition operators: map, filter, reduce
    if (confMethod.isCompositionOperator) {
      val arrValues = reduce(condition.conditions(0)).asInstanceOf[Array[Any]]
      val composeLogic = condition.conditions.slice(1, condition.conditions.length)
      return confMethod.ownerMethodOpt.get.asInstanceOf[CompositionOperator].composeOperator(arrValues, composeLogic, this)
    }

    val conditionsEval = condition.conditions.map(reduce)

    // Global operators: ifElse, merge, in
    if (!confMethod.isReduceType) {
      val methods = confMethod.ownerMethodOpt.get.getClass.getMethods.filter(_.getName == confMethod.methodName).toSet
      val paramTypes = MethodSignatureFinder.maxMins(conditionsEval.getClass, methods.map(_.getParameterTypes.apply(0)))

      if (paramTypes.isEmpty) throw new Error(s"Method ${confMethod.methodName} doesn't accept parameter of type: ${conditionsEval.getClass}")

      val method = methods.find(_.getParameterTypes.apply(0) == paramTypes.head).get
      method.invoke(confMethod.ownerMethodOpt.get, conditionsEval)
    }

    val methodsAndIsOwned = new MethodSignatureFinder(conditionsEval, confMethod).findPaths().head
    println(methodsAndIsOwned.toSeq)

    methodsAndIsOwned
      .zip(conditionsEval.tail)
      .foldLeft[Any](conditionsEval.head){case (conditionEval1, ((method, isMethodOwnedByReducedValue), conditionEval2)) => {
        if (isMethodOwnedByReducedValue) method.invoke(conditionEval1, conditionEval1, conditionEval2)
        else method.invoke(confMethod.ownerMethodOpt.get, conditionEval1, conditionEval2)
      }}
  }

  def reduceValueLogic(condition: ValueLogic[_]): Any = {
    val value = condition.valueOpt.get
    val typeValueOpt = condition.typeCodenameOpt
    if (typeValueOpt.isEmpty) return null

    val reducerClassOpt = conf.valueLogicTypeToReducer.get(typeValueOpt.get)
    reducerClassOpt
      .map(_.newInstance())
      .getOrElse(ReducerValueLogic.identityReducerValueLogic)
      .reduceValueLogic(value)
  }

  def reduce(condition: JsonLogicCore): Any = {
    condition match {
      case composeLogic: ComposeLogic => reduceComposeLogic(composeLogic)
      case valueLogic: ValueLogic[_] => reduceValueLogic(valueLogic)
      case other => throw new IllegalArgumentException(s"Invalid argument: $other")
    }

  }

}
