package com.github.celadari.jsonlogicscala.serialize

import play.api.libs.json.JsValue

trait Marshaller {
  val codename: String
  val className: String

  def marshal(value: Any): JsValue
}
