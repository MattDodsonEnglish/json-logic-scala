package com.github.celadari.jsonlogicscala.tree

object ComposeLogic {

  def unapply(composeLogic: ComposeLogic): Some[(String, Array[JsonLogicCore])] = Some((composeLogic.operator, composeLogic.conditions))
}

class ComposeLogic(override val operator: String, private[this] var _conditions: Array[JsonLogicCore]) extends JsonLogicCore(operator) {

  def conditions: Array[JsonLogicCore] = _conditions
  def conditions_=(conds: Array[JsonLogicCore]): Unit = _conditions = conds

}
