package com.github.celadari.jsonlogicscala.reduce

import java.lang.reflect.Method
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}


object ReduceLogic {



  implicit val reduceLogic: ReduceLogic = new ReduceLogic


}


class ReduceLogic(implicit val conf: ReduceLogicConf) {

  def reduceComposeLogic(condition: ComposeLogic): Any = {
    val conditionsEval = condition.conditions.map(reduce)
    val operator = condition.operator
    val confMethod = conf.operatorToMethodConf(operator)
    val methodName = confMethod.methodName

    conditionsEval.reduce[Any]{case (value1, value2) => {
      val obj = if (confMethod.isExternalMethod) confMethod.ownerMethodOpt.get else value1
      val methods = ReduceLogic.retrieveMethodsFromObj(obj, methodName)
      ReduceLogic.applyValueWithMostFittedMethod(obj, value1, value2, methods)
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
