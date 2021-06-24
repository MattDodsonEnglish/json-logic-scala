package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator
import com.github.celadari.jsonlogicscala.exceptions.{IllegalInputException, WrongNumberOfConditionsException}

object OperatorAt extends Operator {

  def at(values: Array[Any]): Any = {

    if (values.length != 2) throw new WrongNumberOfConditionsException(s"At operator " +
      s"requires length of conditions input to be exactly 2. \nArray of " +
      s"conditions: ${values.mkString("[", ", ", "]")}")

    val index = values(0)

    values(1) match {
      case arr: Array[Any] => arr.apply(index.toString.toDouble.toInt)
      case map: Map[Any, Any] => map.apply(index)
      case ite: Iterable[Any] => ite.toArray.apply(index.toString.toDouble.toInt)
      case other => throw new IllegalInputException(s"At operator second input must be either:" +
        s"Array, Map, Iterable. Current second input: ${other.toString}")
    }
  }
}
