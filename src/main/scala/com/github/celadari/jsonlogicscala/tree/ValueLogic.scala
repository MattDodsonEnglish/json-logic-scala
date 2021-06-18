package com.github.celadari.jsonlogicscala.tree

import com.github.celadari.jsonlogicscala.tree.types.TypeValue


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
                          variableNameOpt: Option[String] = None
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME) {

  /**
   * Indicates if this represents an empty condition.
   * @since 1.1.0
   * @return boolean to indicate if [[ValueLogic.valueOpt]] is [[None]].
   */
  def isEmpty: Boolean = valueOpt.isEmpty
}