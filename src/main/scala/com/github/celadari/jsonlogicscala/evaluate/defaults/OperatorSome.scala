package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.github.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}

object OperatorSome extends CompositionOperator {

  override def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    if (logicArr.length != 1) throw new WrongNumberOfConditionsException(s"Some operator " +
      s"requires length of conditions input to be exactly 1. \nArray of " +
      s"conditions: ${logicArr.mkString("[", ", ", "]")}")

    val jsonLogicComposition = logicArr(0)

    values.exists(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue).asInstanceOf[java.lang.Boolean]
    })
  }
}