package com.github.celadari.jsonlogicscala.tree

import java.util.UUID
import com.github.celadari.jsonlogicscala.exceptions.TreeException
import com.github.celadari.jsonlogicscala.tree.types.TypeValue


object ValueLogic {
  val OPERATOR_CODENAME: String = "var"
}

case class ValueLogic[T](
                          valueOpt: Option[T],
                          typeCodenameOpt: Option[TypeValue] = None,
                          variableNameOpt: Option[String] = None,
                          pathNameOpt: Option[String] = Some(UUID.randomUUID().toString)
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME) {

  if ((variableNameOpt.isDefined && pathNameOpt.isDefined) || (variableNameOpt.isEmpty && pathNameOpt.isEmpty)) {
    throw new TreeException(s"ValueLogic cannot be variable compose and data at the same time: variableNameOpt and pathNameOpt cannot be both defined or empty\n$this")
  }

}