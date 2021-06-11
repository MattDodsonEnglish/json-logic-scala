package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsBoolean, JsNumber, JsValue}

object MarshallerByte extends Marshaller {
  val typeCodename: String = "bool"
  val typeClassName: String = classOf[java.lang.Byte].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toByte)
}
