package com.github.celadari.jsonlogicscala.serialize.impl

import play.api.libs.json.{JsBoolean, JsValue}
import com.github.celadari.jsonlogicscala.serialize.Marshaller

object MarshallerBooleanImpl extends Marshaller {

  def marshal(value: Any): JsValue = JsBoolean(value.toString.toBoolean)
}
