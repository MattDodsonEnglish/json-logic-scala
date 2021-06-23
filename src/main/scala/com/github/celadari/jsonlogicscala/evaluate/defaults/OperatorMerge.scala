package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator
import com.github.celadari.jsonlogicscala.exceptions.WrongInputCondition

object OperatorMerge extends Operator {

  def merge(values: Array[Any]): Array[Any] = {
    values.toList.flatMap{
      case arr: Array[_] => arr
      case _: Map[_, _] => throw new WrongInputCondition("Merge operator does not take map as input condition")
      case other => Array(other)
    }.toArray
  }
}
