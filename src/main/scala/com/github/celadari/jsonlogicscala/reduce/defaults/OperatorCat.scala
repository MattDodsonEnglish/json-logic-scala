package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorCat extends Operator {

  def cat(values: Array[java.lang.String]): java.lang.String = {
    val n = values.length

    if (n != 2) throw new IllegalArgumentException(s"Cat operator: length of array values must be 2")

    values.mkString
  }
}
