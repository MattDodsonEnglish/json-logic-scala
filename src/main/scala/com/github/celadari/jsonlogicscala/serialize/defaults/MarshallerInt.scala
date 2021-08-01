package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.serialize.Marshaller
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object MarshallerInt extends Marshaller {
  val typeCodename: String = INT_CODENAME
  val typeClassName: String = classOf[java.lang.Integer].getName

  def marshal(value: Any): JsValue = {
    value match {
      case intValue: Int => JsNumber(intValue)
      case intValue: java.lang.Integer => JsNumber(intValue.toInt)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerInt: $other.\nMarshallerInt can only be applied to Int values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
