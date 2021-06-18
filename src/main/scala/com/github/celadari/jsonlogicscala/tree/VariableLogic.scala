package com.github.celadari.jsonlogicscala.tree

import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, TypeValue}


case class VariableLogic(variableName: String, composeOperator: ComposeLogic) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME) {
  val typeValue: TypeValue = AnyTypeValue

  def isEmpty: Boolean = throw new IllegalAccessError("Method isEmpty is not defined for VariableLogic type.")
}