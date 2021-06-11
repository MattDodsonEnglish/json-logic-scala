package com.github.celadari.jsonlogicscala.tree.types

object MapTypeValue {
  val CODENAME_TYPE: String = "map"
}

case class MapTypeValue(paramType: TypeValue) extends TypeValue(MapTypeValue.CODENAME_TYPE)
