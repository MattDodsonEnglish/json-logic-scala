package com.github.celadari.jsonlogicscala.deserialize

import play.api.libs.json.JsValue

trait Unmarshaller {
  def unmarshal(jsValue: JsValue): Any
}
