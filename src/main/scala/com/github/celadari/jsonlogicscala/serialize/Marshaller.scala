package com.github.celadari.jsonlogicscala.serialize

import play.api.libs.json.JsValue

trait Marshaller {
  val typeCodename: String
  val typeClassName: String

  def marshal(value: Any): JsValue
}
