package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsValue, JsNumber}

object MarshallerInt extends Marshaller {
  val codename: String = "int"
  val className: String = classOf[Int].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toInt)
}
