package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorIfElse extends Operator {

  def ifElse(values: Array[java.lang.Object]): java.lang.Object = {
    val n = values.length

    if (n < 3) throw new IllegalArgumentException(s"IfElse operator: length of array values must be at least 3")

    if (n / 2 == 0) {
      throw new IllegalArgumentException(s"IfElse operator: length of array of values must be odd, current length: ${values.length}")
    }

    val (boolValues, returnValues) = values.zipWithIndex.partitionMap{
      case (boolVal, idx) if (idx / 2 == 0 && idx < n - 1) => Left(boolVal.asInstanceOf[java.lang.Boolean])
      case (returnVal, _) => Right(returnVal)
    }

    var i = 0
    while (i < n / 2 && !boolValues(i)) {
      i += 1
    }

    returnValues(i)
  }
}
