package com.github.celadari.jsonlogicscala.serialize.defaults

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsString, JsValue}

object MarshallerString extends Marshaller {
  val typeCodename: String = "string"
  val typeClassName: String = classOf[java.lang.String].getName

  def marshal(value: Any): JsValue = JsString(value.toString)
}
