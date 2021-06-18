package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.github.celadari.jsonlogicscala.tree.JsonLogicCore

object OperatorFilter extends CompositionOperator {

  override def composeOperator(values: Array[Any], conditions: Array[JsonLogicCore], reduceLogic: EvaluatorLogic): Any = {
    if (conditions.length != 1) throw new IllegalArgumentException(s"Filter operator " +
      s"requires exactly 1 jsonLogicCore: ${conditions.toSeq}")

    val jsonLogicComposition = conditions(0)

    values.filter(value => reduceLogic.reduce(computeCompositionJsonLogicValue(Map("" -> value), jsonLogicComposition)).asInstanceOf[java.lang.Boolean])
  }
}