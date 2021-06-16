package com.github.celadari.jsonlogicscala.reduce

import java.lang.reflect.Method
import scala.collection.mutable


object MethodSignatureFinder {

  /**
   * Returns minimum super-classes of classOb
   * @example A   B     C
   *         / \ / \  /
   *        D   E   F
   *            \  /
   *             G
   *     maxMins of G is Set(E,F)
   * @param classObj
   * @param classesToScan
   * @return
   */
  def maxMins(classObj: Class[_], classesToScan: Set[Class[_]]): Set[Class[_]] = {
    // retrieve super classes of classObj
    val closeClasses = mutable.Set[Class[_]]()
    closeClasses ++= classesToScan.filter(_.isAssignableFrom(classObj))

    val closests = mutable.Set[Class[_]]()
    while (closeClasses.nonEmpty) {
      val classToExamine = closeClasses.head
      closeClasses -= classToExamine

      // if there is no subclass of classToExamine in neither closeClasses neither closests than save it in closests
      if (!(closeClasses ++ closests).exists(classToExamine.isAssignableFrom)) closests += classToExamine
    }

    closests.toSet
  }
}

class MethodSignatureFinder(
                             val conditionsValues: Array[Any],
                             val confMethod: ReduceLogicConf.MethodConf
                           ) {

  private[this] val conditionsValuesEval = mutable.Stack[Any]() ++ conditionsValues
  private[this] var isEvaluated = false
  private[this] var paths: Set[Array[(Method, Boolean)]] = Set()
  initializePaths()

  private[this] def initializePaths(): Unit = {
    val condValueEval1 = conditionsValuesEval.pop()
    val condValueEval2 = conditionsValuesEval.pop()

    val valueClassMethods = findFirstMethods(condValueEval1, condValueEval1, condValueEval2)
    val classMethods = if (valueClassMethods.nonEmpty) valueClassMethods else findFirstMethods(confMethod.ownerMethodOpt.get, condValueEval1, condValueEval2)

    paths ++= classMethods.map(method => Array((method, valueClassMethods.nonEmpty)))
  }

  private[this] def findFirstMethods(ownerMethod: Any, value1: Any, value2: Any): Set[Method] = {
    ownerMethod
      .getClass
      .getMethods
      .filter(_.getName == confMethod.methodName)
      .filter(method => {
        val class1 = method.getParameterTypes.apply(0)
        val class2 = method.getParameterTypes.apply(1)
        class1.isInstance(value1) && class2.isInstance(value2)
      })
      .toSet
  }

  private[this] def filterExplorable(objClass: Class[_], value: Any, path: Array[(Method, Boolean)]): Set[Method] = {
    val valueClass = value.getClass

    objClass
      .getMethods
      .toSet
      .filter(_.getName == confMethod.methodName)
      .filter(method => {
        val class1 = method.getParameterTypes.apply(0)
        val class2 = method.getParameterTypes.apply(1)
        class1.isAssignableFrom(valueClass) && class2.isAssignableFrom(path.last._1.getReturnType)
      })
  }

  private[this] def explorePath(
                                 value: Any,
                                 ownerMethodOpt: Option[Class[_]],
                                 path: Array[(Method, Boolean)]
                               ): Set[Array[(Method, Boolean)]] = {
    val valueClassMethods = filterExplorable(value.getClass, value, path)
    val classMethods = if (valueClassMethods.nonEmpty) valueClassMethods else filterExplorable(ownerMethodOpt.get, value, path)
    classMethods.map(method => path ++ Array((method, valueClassMethods.nonEmpty)))
  }

  def eval(): Unit = {
    while (conditionsValuesEval.nonEmpty) {
      val value = conditionsValuesEval.pop()
      paths = paths.flatMap(explorePath(value, confMethod.ownerMethodOpt.map(_.getClass), _: Array[(Method, Boolean)]))
    }

    isEvaluated = true
  }

  def squeezeGeneralTypes(methods: Set[(Method, Boolean)]): Set[(Method, Boolean)] = {
    val methodsToExplore = mutable.Set[(Method, Boolean)]() ++ methods

    val mostSpecificMethods = mutable.Set[(Method, Boolean)]()
    while (methodsToExplore.nonEmpty) {
      val (methodToExplore, isMethodOwnedByReducedValue) = methodsToExplore.head
      methodsToExplore -= ((methodToExplore, isMethodOwnedByReducedValue))

      val isSuperClassOfAnotherClass = (methodsToExplore ++ mostSpecificMethods).exists{case (method, _) => {
        methodToExplore.getParameterTypes.apply(0).isAssignableFrom(method.getParameterTypes.apply(0)) &&
          methodToExplore.getParameterTypes.apply(1).isAssignableFrom(method.getParameterTypes.apply(1))
      }}
      if (!isSuperClassOfAnotherClass) mostSpecificMethods += ((methodToExplore, isMethodOwnedByReducedValue))
    }

    mostSpecificMethods.toSet
  }

  def optimizePaths(): Unit = {
    val n = paths.headOption.map(_.length).getOrElse(0)

    for (i <- 0 until n) {
      val methodsToExplore = mutable.Set[(Method, Boolean)]() ++ paths.map(_(i))

      val mostSpecificMethods = mutable.Set[(Method, Boolean)]()
      while (methodsToExplore.nonEmpty) {
        val (methodToExplore, isMethodOwnedByReducedValue) = methodsToExplore.head
        methodsToExplore -= ((methodToExplore, isMethodOwnedByReducedValue))

        val isSuperClassOfAnotherClass = (methodsToExplore ++ mostSpecificMethods).exists{case (method, _) => {
          methodToExplore.getParameterTypes.apply(0).isAssignableFrom(method.getParameterTypes.apply(0)) &&
          methodToExplore.getParameterTypes.apply(1).isAssignableFrom(method.getParameterTypes.apply(1))
        }}
        if (!isSuperClassOfAnotherClass) mostSpecificMethods += ((methodToExplore, isMethodOwnedByReducedValue))
      }

      paths = paths.filter(path => mostSpecificMethods.contains(path(i)))
    }
  }

  def findPaths(): Set[Array[(Method, Boolean)]] = {
    if (!isEvaluated) {
      eval()
      optimizePaths()
    }
    paths
  }
}
