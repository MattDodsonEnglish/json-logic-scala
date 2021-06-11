package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.JsValue
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller

object UnmarshallerFloat extends Unmarshaller{
  def unmarshal(jsValue: JsValue): Any = jsValue.as[Float]
}
