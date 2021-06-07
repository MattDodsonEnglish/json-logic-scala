package com.github.celadari.jsonlogicscala.deserialize.defaults

import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.deserialize.defaults2.default_unmarshaller
import play.api.libs.json.JsValue

//@default_unmarshaller
object UnmarshallerBoolean extends Unmarshaller {
  def unmarshal(jsValue: JsValue): Any = jsValue.as[Boolean]
}

object u {

}