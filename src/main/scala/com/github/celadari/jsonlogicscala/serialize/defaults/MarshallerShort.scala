package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsNumber, JsValue}

object MarshallerShort extends Marshaller {
  val typeCodename: String = "short"
  val typeClassName: String = classOf[java.lang.Short].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toByte)
}
