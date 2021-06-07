package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsString, JsValue}

object MarshallerString extends Marshaller {
  val codename: String = "string"
  val className: String = classOf[String].getName

  def marshal(value: Any): JsValue = JsString(value.toString)
}
