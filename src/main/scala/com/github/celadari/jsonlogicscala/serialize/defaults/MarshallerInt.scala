package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsValue, JsNumber}

object MarshallerInt extends Marshaller {
  val typeCodename: String = "int"
  val typeClassName: String = classOf[java.lang.Integer].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toInt)
}
