package com.github.celadari.jsonlogicscala.evaluate

object EvaluatorValueLogic {
  val identityReducerValueLogic: EvaluatorValueLogic = new EvaluatorValueLogic {
    override def evaluateValueLogic(value: Any): Any = value
  }
}

trait EvaluatorValueLogic {

  def evaluateValueLogic(value: Any): Any
}
