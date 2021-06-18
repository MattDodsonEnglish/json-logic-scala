package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorSubstr extends Operator {

  def substr(values: Array[java.lang.Object]): java.lang.String = {
    val n = values.length

    if (n < 2) throw new IllegalArgumentException(s"Subst operator: length of array values must not be less than 2")
    if (n > 3) throw new IllegalArgumentException(s"Subst operator: length of array values must not be greater than 3")

    val string = values(0).asInstanceOf[java.lang.String]
    val idx = values(1).asInstanceOf[java.lang.Number].intValue()
    val len = values(2).asInstanceOf[java.lang.Number].intValue()
    val length = string.length

    val startIdx = if (idx < 0) (idx + length) % length else idx
    val endIdx = if (len < 0) (length - len) else (idx + len)

    string.slice(startIdx, endIdx)
  }
}
