package com.github.celadari.jsonlogicscala.reduce

import java.lang.reflect.Method

object Operator {
/*
  import com.github.celadari.jsonlogicscala.order.ClassRuntimeOrder
  implicit val orderClass: Ordering[Class[_]] = ClassRuntimeOrder.orderClass
  implicit val orderClassOpt: Ordering[Option[Class[_]]] = ClassRuntimeOrder.orderClassOpt
*/
  /*
  private[this] def applyValueWithMostFittedMethod(obj: Any, value1: Any, value2: Any, methods: Array[Method]): Any = {
    var methodsSeq = methods.toSeq
    var valueOpt: Option[Any] = None
    while(methodsSeq.nonEmpty) {
      try {
        valueOpt = Some(methodsSeq.head.invoke(obj, value1, value2))
        methodsSeq = Seq()
      }
      catch {
        case _: java.lang.IllegalArgumentException => methodsSeq = methodsSeq.tail
        case _: Throwable => methodsSeq = methodsSeq.tail
      }
    }
    valueOpt.getOrElse(throw new IllegalArgumentException("Reduce computed a null result"))
  }

  private[this] def retrieveMethodsFromObj(obj: Any, methodName: String): Array[Method] = {
    obj
      .getClass
      .getMethods
      .filter(_.getName == methodName)
      .sortBy(method => {
        (method.getParameterTypes.headOption, method.getParameterTypes.lift(1))
      })
  }

  def getPaths(class1: Class[_], class2: Class[_]): Set[Array[(Class[], Class[_])] = {

  }

  def getMethods(conditionsValues: Array[Any], confMethod: ReduceLogicConf.MethodConf): Array[Method] = {
    val methods = mutable.ArrayBuffer[Method]()

    val classes = mutable.Stack()

    val classesVal = conditionsValues.map(_.getClass).toSet

    var u1: Class[_] = null
    var u2: Class[_] = null
    for (value <- conditionsValues) {
      val getClass = value.getClass
      val maxMins = ClassRuntimeOrder.maxMins()


    }

  }

  def getEvaluatedValue(conditionsValues: Array[Any], confMethod: ReduceLogicConf.MethodConf): Any = {
    val methods: Array[Method] = null

    val isReducableOperator = conditionsValues.head.getClass.getMethods

    conditionsValues.reduceLeft()


    if (confMethod.isExternalMethod) confMethod.ownerMethodOpt.get.eval(conditionsValues, confMethod)
    else {
      conditionsValues.zipWithIndex.reduce[(Any, Int)]{case ((evaluedVal, idx), (value, _)) => {
        (methods(idx).invoke(evaluedVal, evaluedVal, value), idx + 1)
      }}._1



      /*
      conditionsValues.zipWithIndex.reduce[Any]{case (value, idx), => {
        val obj = if (confMethod.isExternalMethod) confMethod.ownerMethodOpt.get else value1
        val methods = ReduceLogic.retrieveMethodsFromObj(obj, methodName)
        Operator.applyValueWithMostFittedMethod(obj, value1, value2, methods)
      }}*/
    }
  }
*/

}


trait Operator {

  def evaluate(conditionsValues: Array[Any], confMethod: ReduceLogicConf.MethodConf): Any = {
    val methods: Array[Method] = null

    conditionsValues.zipWithIndex.reduce[(Any, Int)]{case ((evaluedVal, idx), (value, _)) => {
      (methods(idx).invoke(this, evaluedVal, value), idx + 1)
    }}._1
  }


}
