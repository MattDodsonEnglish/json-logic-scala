package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.{CompositionOperator, ReduceLogic}
import com.github.celadari.jsonlogicscala.tree.JsonLogicCore

object OperatorFilter extends CompositionOperator {

  override def composeOperator(values: Array[Any], conditions: Array[JsonLogicCore], reduceLogic: ReduceLogic): Any = {
    if (conditions.length != 1) throw new IllegalArgumentException(s"Filter operator " +
      s"requires exactly 1 jsonLogicCore: ${conditions.toSeq}")

    val jsonLogicComposition = conditions(0)

    values.filter(value => reduceLogic.reduce(computeCompositionJsonLogicValue(Map("" -> value), jsonLogicComposition)).asInstanceOf[java.lang.Boolean])
  }
}