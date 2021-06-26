package com.github.celadari.jsonlogicscala.tree

import com.github.celadari.jsonlogicscala.exceptions.TreeException
import com.github.celadari.jsonlogicscala.tree.types.TypeValue

import java.util.UUID


object ValueLogic {

  val OPERATOR_CODENAME: String = "var"

  /**
   * Returns an empty condition.
   * @since 1.1.0
   * @return [[ValueLogic]] instance.
   */
  def empty[T]: ValueLogic[T] = new ValueLogic[T](None, None)

}

case class ValueLogic[T](
                          valueOpt: Option[T],
                          typeCodenameOpt: Option[TypeValue] = None,
                          variableNameOpt: Option[String] = None,
                          pathNameOpt: Option[String] = Some(UUID.randomUUID().toString)
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME) {

  if ((variableNameOpt.isDefined && pathNameOpt.isDefined) || (variableNameOpt.isEmpty && pathNameOpt.isEmpty)) {
    throw new TreeException("ValueLogic cannot be variable compose and data at the same time")
  }
  /**
   * Indicates if this represents an empty condition.
   * @since 1.1.0
   * @return boolean to indicate if [[ValueLogic.valueOpt]] is [[None]].
   */
  def isEmpty: Boolean = valueOpt.isEmpty
  def isVariableCompose: Boolean = variableNameOpt.isDefined

}