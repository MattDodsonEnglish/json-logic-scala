package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorAt extends Operator {

  def at(values: Array[java.lang.Object]): java.lang.Object = {
    val n = values.length

    if (n != 2) throw new IllegalArgumentException(s"At operator: length of array values must be at exactly 2")

    val index = values(0)

    val valueAt = values(1) match {
      case arr: Array[Any] => arr.apply(index.toString.toInt)
      case map: Map[Any, Any] => map.apply(index)
      case ite: Iterable[Any] => ite.toArray.apply(index.toString.toInt)
    }
    valueAt.asInstanceOf[java.lang.Object]
  }
}
