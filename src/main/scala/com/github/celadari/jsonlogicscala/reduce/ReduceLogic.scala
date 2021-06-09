package com.github.celadari.jsonlogicscala.reduce

import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}

import java.lang.reflect.Method

object ReduceLogic {

  import com.github.celadari.jsonlogicscala.ordering.ClassRuntimeOrder
  implicit val orderClass: Ordering[Class[_]] = ClassRuntimeOrder.orderClass
  implicit val orderClassOpt: Ordering[Option[Class[_]]] = ClassRuntimeOrder.orderClassOpt

  implicit val reduceLogic: ReduceLogic = new ReduceLogic

  private def retrieveMethodsFromObj(obj: Any, methodName: String): Array[Method] = {
    obj
      .getClass
      .getMethods
      .filter(_.getName == methodName)
      .sortBy(method => {
        (method.getParameterTypes.headOption, method.getParameterTypes.lift(1))
      })
  }

  private def applyValueWithMostFittedMethod(obj: Any, value1: Any, value2: Any, methods: Array[Method]): Any = {
    var methodsSeq = methods.toSeq
    var valueOpt: Option[Any] = None
    while(methodsSeq.nonEmpty) {
      try {
        valueOpt = Some(methodsSeq.head.invoke(obj, value1, value2))
        methodsSeq = Seq()
      }
      catch {
        case _: java.lang.IllegalArgumentException => methodsSeq = methodsSeq.tail
        case _ => methodsSeq = methodsSeq.tail
      }
    }
    valueOpt.getOrElse(throw new IllegalArgumentException("Reduce computed a null result"))
  }
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
    val reducerClass = conf.classNameToReducer(value.getClass.getCanonicalName)
    reducerClass.newInstance().asInstanceOf[ReducerValueLogic].reduceValueLogic(value)
  }

  def reduce(condition: JsonLogicCore): Any = {
    condition match {
      case composeLogic: ComposeLogic => reduceComposeLogic(composeLogic)
      case valueLogic: ValueLogic[_] => reduceValueLogic(valueLogic)
      case other => throw new IllegalArgumentException(s"Invalid argument: $other")
    }

  }

}
