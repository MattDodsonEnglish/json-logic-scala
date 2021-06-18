package com.github.celadari.jsonlogicscala.tree.types

object TypeVariable {
  val CODENAME_TYPE: String = "composeVariable"

}

case class TypeVariable(variableName: String) extends TypeValue(TypeVariable.CODENAME_TYPE)