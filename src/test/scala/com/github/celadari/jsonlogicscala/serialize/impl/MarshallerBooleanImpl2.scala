package com.github.celadari.jsonlogicscala.serialize.impl

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsBoolean, JsValue}

object MarshallerBooleanImpl2 extends Marshaller {

  def marshal(value: Any): JsValue = JsBoolean(value.toString.toBoolean)
}
