package com.github.celadari.jsonlogicscala.reduce


import java.lang.reflect.Method
import scala.collection.mutable

class MethodSignatureFinder(
                             val conditionsValues: Array[Any],
                             val confMethod: ReduceLogicConf.MethodConf
                           ) {

  private[this] val conditionsValuesEval = mutable.Stack[Any]() ++ conditionsValues
  private[this] var isEvaluated = false
  private[this] val firstClass = conditionsValuesEval.pop().getClass
  private[this] var paths: Set[Array[Method]] = Set(firstClass.getMethods.filter(_.getName == confMethod.methodName))

  /*
  private[this] def findMethods(valueClass: Class[_]): Set[Method] = {
    valueClass.getMethods.toSet.filter(_.getName == confMethod.methodName).filter(method => {
      val class1 = method.getParameterTypes.apply(0)
      val class2 = method.getParameterTypes.apply(1)
      class1.isAssignableFrom(valueClass) && paths.exists(arr => class2.isAssignableFrom(arr.last._3))
    })
  }*/

  private[this] def explorePath(
                                 valueClass: Class[_],
                                 objClassMethods: Class[_],
                                 path: Array[Method]
                               ): Set[Array[Method]] = {
    objClassMethods
      .getMethods
      .toSet
      .filter(_.getName == confMethod.methodName)
      .filter(method => {
        val class1 = method.getParameterTypes.apply(0)
        val class2 = method.getParameterTypes.apply(1)
        class1.isAssignableFrom(valueClass) && class2.isAssignableFrom(path.last.getReturnType)
      })
      .map(method => path ++ Array(method))
  }

  def eval(): Unit = {
    while (conditionsValuesEval.nonEmpty) {
      val valueClass = conditionsValuesEval.pop().getClass
      //val methodsValueClass = findMethods(valueClass)

      val pathMethodsValueClass = paths.flatMap(explorePath(valueClass, valueClass, _: Array[Method]))

      if (pathMethodsValueClass.nonEmpty) paths = pathMethodsValueClass
      else {
        val objClass = confMethod.ownerMethodOpt.map(_.getClass).get
        paths = paths.map(explorePath(valueClass, objClass, _: Array[Method]))
      }
    }

    isEvaluated = true
  }

  def optimizePaths(): Unit = {
    val n = paths.headOption.map(_.length).getOrElse(0)
    for (i <- 0 until n) {
      val methods = paths.map(_(i))

    }
  }

  def find(): Set[Array[Method]] = {
    if (!isEvaluated) {
      eval()

    }
    return paths
  }
}
