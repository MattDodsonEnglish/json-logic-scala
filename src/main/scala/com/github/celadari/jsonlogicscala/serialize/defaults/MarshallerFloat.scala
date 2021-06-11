package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsNumber, JsValue}

object MarshallerFloat extends Marshaller {
  val typeCodename: String = "float"
  val typeClassName: String = classOf[java.lang.Double].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toFloat)
}
