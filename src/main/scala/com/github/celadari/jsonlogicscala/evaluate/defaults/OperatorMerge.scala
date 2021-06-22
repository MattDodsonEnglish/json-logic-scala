package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorMerge extends Operator {

  def merge(values: Array[Any]): Array[Any] = {
    values.toList.flatMap{
      case arr: Array[_] => arr
      case _: Map[_, _] => throw new IllegalArgumentException("Merge operator does not take map as inputs")
      case other => Array(other)
    }.toArray
  }
}
