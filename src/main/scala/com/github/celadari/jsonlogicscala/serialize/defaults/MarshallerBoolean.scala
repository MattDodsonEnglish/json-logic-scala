package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsBoolean, JsNumber, JsValue}

object MarshallerBoolean extends Marshaller {
  val typeCodename: String = "bool"
  val typeClassName: String = classOf[java.lang.Boolean].getName

  def marshal(value: Any): JsValue = JsBoolean(value.toString.toBoolean)
}