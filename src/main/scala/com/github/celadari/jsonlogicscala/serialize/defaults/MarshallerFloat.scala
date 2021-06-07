package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsNumber, JsValue}

object MarshallerFloat extends Marshaller {
  val codename: String = "float"
  val className: String = classOf[Float].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toFloat)
}
