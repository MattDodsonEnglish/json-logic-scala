package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}

object OperatorReduce extends CompositionOperator {

  override def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    if (logicArr.length != 2) throw new IllegalArgumentException(s"Map operator " +
      s"requires exactly 1 jsonLogicCore: ${logicArr.toSeq}")

    val jsonLogicComposition = logicArr(0)

    values.reduce[Any]{case (value1, value2) => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("accumulator" -> value1, "current" -> value2))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue)
    }}
  }
}