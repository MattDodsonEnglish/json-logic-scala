package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.github.celadari.jsonlogicscala.serialize.Marshaller

object MarshallerInt extends Marshaller {
  val typeCodename: String = INT_CODENAME
  val typeClassName: String = classOf[java.lang.Integer].getName

  def marshal(value: Any): JsValue = JsNumber(value.toString.toInt)
}
