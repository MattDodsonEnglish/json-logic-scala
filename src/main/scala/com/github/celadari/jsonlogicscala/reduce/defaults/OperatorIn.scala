package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorIn extends Operator {

  def in(values: Array[java.lang.Object]): java.lang.Boolean = {
    val n = values.length

    if (n != 2) throw new IllegalArgumentException(s"In operator: length of array values must be 2")

    val value = values(0)
    val arr = values(1).asInstanceOf[Array[Any]]
    arr.contains(value)
  }

  def in(values: Array[java.lang.String]): java.lang.Boolean = {
    val n = values.length

    if (n != 2) throw new IllegalArgumentException(s"In operator: length of array values must be 2")

    val subString = values(0)
    val string = values(1)
    string.contains(subString)
  }
}
