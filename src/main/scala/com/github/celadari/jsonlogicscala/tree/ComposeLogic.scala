package com.github.celadari.jsonlogicscala.tree

object ComposeLogic {

  /**
   * Returns an empty condition.
   * @since 1.0.0
   * @return [[ComposeLogic]] instance.
   */
  def empty: ComposeLogic = new ComposeLogic("", Array())

  def unapply(composeLogic: ComposeLogic): Some[(String, Array[JsonLogicCore])] = Some((composeLogic.operator, composeLogic.conditions))
}

class ComposeLogic(override val operator: String, private[this] var _conditions: Array[JsonLogicCore]) extends JsonLogicCore(operator) {

  /**
   * Indicates if this represents an empty condition.
   * @since 1.1.0
   * @return boolean to indicate if all sub-conditions are empty as well or if sub-conditions array is empty.
   */
  def isEmpty: Boolean = conditions.forall(_.isEmpty)

  def conditions: Array[JsonLogicCore] = _conditions
  def conditions_=(conds: Array[JsonLogicCore]): Unit = _conditions = conds

}
