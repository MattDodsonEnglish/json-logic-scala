package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsNumber, JsValue}

object MarshallerDouble extends Marshaller {
  val codename: String = "double"
  val className: String = classOf[Double].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toDouble)
}
