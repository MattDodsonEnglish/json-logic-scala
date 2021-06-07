package com.github.celadari.jsonlogicscala.core

object ComposeLogic {

  object BINARY_OPERATORS {
    val LTEQ: String = "<="
    val LT: String = "<"
    val GTEQ: String = ">="
    val GT: String = ">"
    val EQ: String = "=="
    val DIFF: String = "!="
    val IN: String = "in"
    val NOT_IN: String = "not in"
    val ALL: Array[String] = Array(LTEQ, LT, GTEQ, GT, EQ, DIFF)
  }

  object MULTIPLE_OPERATORS {
    val OR: String = "or"
    val AND: String = "and"
    val XOR: String = "xor"
    val MAX: String = "max"
    val MIN: String = "min"
    val ALL: Array[String] = Array(OR, AND, XOR, MAX, MIN)
  }
  val OPERATORS: Array[String] = BINARY_OPERATORS.ALL ++ MULTIPLE_OPERATORS.ALL

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
