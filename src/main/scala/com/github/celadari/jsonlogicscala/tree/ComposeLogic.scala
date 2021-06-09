package com.github.celadari.jsonlogicscala.tree

object ComposeLogic {

  /**
   * Returns an empty condition.
   * @since 1.0.0
   * @return [[ComposeLogic]] instance.
   */
  def empty: ComposeLogic = new ComposeLogic("", Array())

}

case class ComposeLogic(override val operator: String, conditions: Array[JsonLogicCore]) extends JsonLogicCore(operator) {

  /**
   * Indicates if this represents an empty condition.
   * @since 1.1.0
   * @return boolean to indicate if all sub-conditions are empty as well or if sub-conditions array is empty.
   */
  def isEmpty: Boolean = conditions.forall(_.isEmpty)
}
