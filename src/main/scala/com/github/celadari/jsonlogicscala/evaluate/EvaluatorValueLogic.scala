package com.github.celadari.jsonlogicscala.evaluate

object EvaluatorValueLogic {
  val identityReducerValueLogic: EvaluatorValueLogic = new EvaluatorValueLogic {
    override def reduceValueLogic(value: Any): Any = value
  }
}

trait EvaluatorValueLogic {

  def reduceValueLogic(value: Any): Any
}
