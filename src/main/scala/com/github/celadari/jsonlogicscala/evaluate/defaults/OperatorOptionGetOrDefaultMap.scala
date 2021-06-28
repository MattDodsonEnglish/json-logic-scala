package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object OperatorOptionGetOrDefaultMap extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(map) => {
        map match {
          case _: Map[_, _] => map
          case _ => throw new IllegalInputException(s"Operator OptionToMap can only be " +
            s"applied to Option[Map[_, _]] or Map[_, _] values. Input conditon: $value")
        }
      }
      case None => Map[Any, Any]()
      case _: Map[_, _] => value
      case _ => throw new IllegalInputException(s"Operator OptionToMap can only be " +
        s"applied to Option[Map[_, _]] or Map[_, _] values. Input conditon: $value")
    }
  }
}