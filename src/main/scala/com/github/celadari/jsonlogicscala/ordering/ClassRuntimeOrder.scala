package com.github.celadari.jsonlogicscala.ordering

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

}