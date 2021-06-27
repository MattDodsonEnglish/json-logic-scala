package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerLong extends Unmarshaller{
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toLong
      case other => throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerLong: ${other}." +
        s"\nUnmarshallerLong could not unmarshall to Long value." +
        "\nCheck if \"type\" and \"var\" are correct.")
    }
  }
}
