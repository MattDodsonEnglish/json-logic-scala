package com.github.celadari.jsonlogicscala.reduce

import com.github.celadari.jsonlogicscala.tree.types.TypeVariable
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}

object CompositionOperator {

  class ComposeJsonLogicCore(private[this] val variableNameToValue: Map[String, Any], private[this] val jsonLogicVariable: JsonLogicCore) {
    private[this] var jsonLogicTraversed: JsonLogicCore = _
    private[this] var isEvaluated: Boolean = false

    private[this] def traverse(jsonLogicCore: JsonLogicCore): JsonLogicCore = {
      jsonLogicCore match {
        case ComposeLogic(operator, conditions) => ComposeLogic(operator, conditions.map(traverse))
        case ValueLogic(_, typeCodenameOpt: Some[TypeVariable]) => ValueLogic(Some(variableNameToValue(typeCodenameOpt.get.variableName)), typeCodenameOpt)
        case ValueLogic(_, _) => jsonLogicCore
      }
    }

    def evaluate(): JsonLogicCore = {
      if (!isEvaluated) {
        jsonLogicTraversed = traverse(jsonLogicVariable)
        isEvaluated = true
      }
      jsonLogicTraversed
    }
  }

}

trait CompositionOperator extends Operator {

  protected def computeCompositionJsonLogicValue(variableNameToValue: Map[String, Any], jsonLogicCore: JsonLogicCore): JsonLogicCore = {
    new CompositionOperator.ComposeJsonLogicCore(variableNameToValue, jsonLogicCore).evaluate()
  }

  def composeOperator(values: Array[Any], jsonLogicCore: Array[JsonLogicCore], reduceLogic: ReduceLogic): Any
}
