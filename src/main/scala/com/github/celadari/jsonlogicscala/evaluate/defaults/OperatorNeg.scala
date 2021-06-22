package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.{Operator, UnaryOperator}

object OperatorNeg extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case bool: Boolean => !bool
      case bool: java.lang.Boolean => !bool
      case _ => throw new IllegalArgumentException(s"OperatorNeg can only be applied to boolean values: $value")
    }
  }
}