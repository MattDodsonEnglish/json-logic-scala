package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator
import com.github.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException

object OperatorIfElse extends Operator {

  def ifElse(values: Array[java.lang.Object]): java.lang.Object = {
    val n = values.length

    if (n < 3) throw new WrongNumberOfConditionsException(s"IfElse operator " +
      s"requires length of conditions to be at least 3. \nArray of " +
      s"conditions: ${values.mkString("[", ", ", "]")}")

    if (n % 2 == 0) {
      throw new WrongNumberOfConditionsException(s"IfElse operator " +
        s"requires length of conditions to be odd. \nArray of conditions: ${values.mkString("[", ", ", "]")}")
    }

    val (boolValues, returnValues) = values.zipWithIndex.partitionMap{
      case (boolVal, idx) if (idx % 2 == 0 && idx < n - 1) => {
        Left(boolVal.asInstanceOf[java.lang.Boolean])
      }
      case (returnVal, _) => Right(returnVal)
    }

    var i = 0
    while (i < n / 2 && !boolValues(i)) {
      i += 1
    }

    returnValues(i)
  }
}
