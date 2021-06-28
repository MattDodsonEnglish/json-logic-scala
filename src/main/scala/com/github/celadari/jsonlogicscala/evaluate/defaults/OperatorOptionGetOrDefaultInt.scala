package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object OperatorOptionGetOrDefaultInt extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(i) => {
        i match {
          case _: Int => i
          case _: java.lang.Integer => i
          case _ => throw new IllegalInputException(s"Operator OptionToInt can only be " +
            s"applied to Option[Int] or Int values. Input conditon: $value")
        }
      }
      case None => 0
      case _: Int => value
      case _: java.lang.Integer => value
      case _ => throw new IllegalInputException(s"Operator OptionToInt can only be " +
        s"applied to Option[Int] or Int values. Input conditon: $value")
    }
  }
}