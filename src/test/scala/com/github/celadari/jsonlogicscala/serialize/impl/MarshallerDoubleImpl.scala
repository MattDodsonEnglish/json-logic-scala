package com.github.celadari.jsonlogicscala.serialize.impl

import com.github.celadari.jsonlogicscala.serialize.Marshaller
import play.api.libs.json.{JsNumber, JsValue}

class MarshallerDoubleImpl(offset: Double) extends Marshaller {

  override def toString: String = this.getClass.getName
  def marshal(value: Any): JsValue = {
    JsNumber(value.toString.toDouble + offset)
  }
}
