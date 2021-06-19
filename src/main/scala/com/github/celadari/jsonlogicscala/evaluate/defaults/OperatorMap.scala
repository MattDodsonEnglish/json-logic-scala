package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}

object OperatorMap extends CompositionOperator {

  override def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    if (logicArr.length != 1) throw new IllegalArgumentException(s"Map operator " +
      s"requires exactly 1 jsonLogicCore: ${logicArr.toSeq}")

    val jsonLogicComposition = logicArr(0)

    values.map(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue)
    })
  }
}