package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object OperatorOptionGetOrDefaultString extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(s) => {
        s match {
          case string: String => string
          case _ => throw new IllegalInputException(s"Operator OptionToString can only be " +
            s"applied to Option[String] or String values. Input conditon: $value")
        }
      }
      case None => ""
      case string: String => string
      case _ => throw new IllegalInputException(s"Operator OptionToString can only be " +
        s"applied to Option[String] or String values. Input conditon: $value")
    }
  }
}