package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNull, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerNull extends Unmarshaller {
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNull => null
      case other => throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerNull: ${other}." +
        s"\nUnmarshallerNull can only be applied to null value." +
        "\nCheck if \"type\" and \"var\" are correct.")
    }
  }
}