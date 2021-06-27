package com.github.celadari.jsonlogicscala.deserialize.defaults

import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import play.api.libs.json.JsValue

object UnmarshallerNull extends Unmarshaller {
  def unmarshal(jsValue: JsValue): Any = null: Any
}