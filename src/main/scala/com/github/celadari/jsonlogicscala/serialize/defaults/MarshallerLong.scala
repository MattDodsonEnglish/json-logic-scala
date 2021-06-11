package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsNumber, JsValue}

object MarshallerLong extends Marshaller {
  val typeCodename: String = "long"
  val typeClassName: String = classOf[java.lang.Long].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toLong)
}
