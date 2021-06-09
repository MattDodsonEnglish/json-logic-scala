package com.github.celadari.jsonlogicscala.tree


object ValueLogic {

  /**
   * Returns an empty condition.
   * @since 1.1.0
   * @return [[ValueLogic]] instance.
   */
  def empty[T]: ValueLogic[T] = new ValueLogic[T]("", None)

}

case class ValueLogic[T](
                          override val operator: String,
                          valueOpt: Option[T],
                          typeCodenameOpt: Option[String] = None,
                        ) extends JsonLogicCore(operator) {

  /**
   * Indicates if this represents an empty condition.
   * @since 1.1.0
   * @return boolean to indicate if [[ValueLogic.valueOpt]] is [[None]].
   */
  def isEmpty: Boolean = valueOpt.isEmpty
}