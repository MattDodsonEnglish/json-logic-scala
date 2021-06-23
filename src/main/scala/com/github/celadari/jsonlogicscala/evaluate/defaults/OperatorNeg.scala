package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.WrongInputCondition

object OperatorNeg extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case bool: Boolean => !bool
      case bool: java.lang.Boolean => !bool
      case other => throw new WrongInputCondition(s"Operator Neg can only be " +
        s"applied to boolean values. Input conditon: ${other.toString}")
    }
  }
}