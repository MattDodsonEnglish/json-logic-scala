package com.github.celadari.jsonlogicscala.tree.types

object ArrayTypeValue {
  val CODENAME_TYPE: String = "array"
}

case class ArrayTypeValue(paramType: TypeValue) extends TypeValue(ArrayTypeValue.CODENAME_TYPE)