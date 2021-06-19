package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}

object OperatorFilter extends CompositionOperator {

  override def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    if (logicArr.length != 1) throw new IllegalArgumentException(s"Filter operator " +
      s"requires exactly 1 jsonLogicCore: ${logicArr.toSeq}")

    val jsonLogicComposition = logicArr(0)

    values.filter(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.reduce(jsonLogicComposition, newLogicOperatorToValue).asInstanceOf[java.lang.Boolean]
    })
  }
}