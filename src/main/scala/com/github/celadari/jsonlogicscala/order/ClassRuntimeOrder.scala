package com.github.celadari.jsonlogicscala.order

import scala.collection.mutable

object ClassRuntimeOrder {
  private[this] val assignedWeights1: Map[Class[_], Int] = Map(
    classOf[java.lang.Byte] -> 1,
    classOf[java.lang.Short] -> 2,
    classOf[java.lang.Integer] -> 3,
    classOf[java.lang.Long] -> 4,
    classOf[java.lang.Float] -> 5,
    classOf[java.lang.Double] -> 6
  )
  private[this] val assignedWeights2: Map[Class[_], Int] = Map(
    classOf[java.lang.Character] -> 1,
    classOf[java.lang.Integer] -> 2,
    classOf[java.lang.Long] -> 3,
    classOf[java.lang.Float] -> 4,
    classOf[java.lang.Double] -> 5
  )
  private[this] val DEFAULT_CLASSES1 = assignedWeights1.keySet
  private[this] val DEFAULT_CLASSES2 = assignedWeights2.keySet

  private[this] def _compare(x: Class[_], y: Class[_]): Int = {
    if (x.isAssignableFrom(y)) 1
    else if (y.isAssignableFrom(x)) -1
    else if (DEFAULT_CLASSES1.contains(x) && DEFAULT_CLASSES1.contains(y)) assignedWeights1(x) - assignedWeights1(y)
    else if (DEFAULT_CLASSES2.contains(x) && DEFAULT_CLASSES2.contains(y)) assignedWeights2(x) - assignedWeights2(y)
    else 0
  }

  implicit val orderClass: Ordering[Class[_]] = new Ordering[Class[_]] {
    def compare(x: Class[_], y: Class[_]): Int = _compare(x, y)
  }

  implicit val orderClassOpt: Ordering[Option[Class[_]]] = new Ordering[Option[Class[_]]] {
    def compare(x: Option[Class[_]], y: Option[Class[_]]): Int = {
      if (x.isEmpty || y.isEmpty) 0
      else _compare(x.get, y.get)
    }
  }
/*
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
  }*/
/*
  def maxMins(classObj1: Class[_], classObj2: Class[_], classesToScan: Set[(Class[_], Class[_])]): Set[(Class[_], Class[_])] = {
    // retrieve super classes of classObj
    val closeClasses = mutable.Set[(Class[_], Class[_])]()
    closeClasses ++= classesToScan.filter{case (class1, class2) =>  class1.isAssignableFrom(classObj1) && class1.isAssignableFrom(classObj1)}

    val closests = mutable.Set[(Class[_], Class[_])]()
    while (closeClasses.nonEmpty) {
      val (classToExamine1, classToExamine2) = closeClasses.head
      closeClasses -= (classToExamine1, classToExamine2)

      // if there is no subclass of classToExamine in neither closeClasses neither closests than save it in closests
      if (!(closeClasses ++ closests).exists{case (class1, class2) =>
        classToExamine1.isAssignableFrom(class1) && classToExamine2.isAssignableFrom(class2)}
      ) closests += (classToExamine1, classToExamine2)
    }

    closests.toSet
  }*/

}