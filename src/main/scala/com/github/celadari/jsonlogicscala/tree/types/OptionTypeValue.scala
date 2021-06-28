package com.github.celadari.jsonlogicscala.tree.types

object OptionTypeValue {
  val CODENAME_TYPE: String = "option"
}

case class OptionTypeValue(paramType: TypeValue) extends TypeValue(OptionTypeValue.CODENAME_TYPE)