package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.{CompositionOperator, ReduceLogic}
import com.github.celadari.jsonlogicscala.tree.JsonLogicCore

object OperatorReduce extends CompositionOperator {

  override def composeOperator(values: Array[Any], conditions: Array[JsonLogicCore], reduceLogic: ReduceLogic): Any = {
    if (conditions.length != 2) throw new IllegalArgumentException(s"Map operator " +
      s"requires exactly 1 jsonLogicCore: ${conditions.toSeq}")

    val jsonLogicComposition = conditions(0)

    values.reduce{case (value1, value2) => {
      val jsonLogicToBeReduced = computeCompositionJsonLogicValue(Map("accumulator" -> value1, "current" -> value2), jsonLogicComposition)
      reduceLogic.reduce(jsonLogicToBeReduced)
    }}
  }
}