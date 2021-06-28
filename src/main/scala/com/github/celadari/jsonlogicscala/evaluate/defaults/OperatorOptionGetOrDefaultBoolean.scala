package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object OperatorOptionGetOrDefaultBoolean extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(bool) => {
        bool match {
          case _: Boolean => bool
          case _: java.lang.Boolean => bool
          case _ => throw new IllegalInputException(s"Operator OptionToBoolean can only be " +
            s"applied to Option[Boolean] or Boolean values. Input conditon: $value")
        }
      }
      case None => false
      case _: Boolean => value
      case _: java.lang.Boolean => value
      case _ => throw new IllegalInputException(s"Operator OptionToBoolean can only be " +
        s"applied to Option[Boolean] or Boolean values. Input conditon: $value")
    }
  }
}