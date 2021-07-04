package com.github.celadari.jsonlogicscala.evaluate.impl

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic

object EvaluatorValueLogicImplString extends EvaluatorValueLogic {

  override def toString: String = this.getClass.getName
  override def evaluateValueLogic(value: Any): Any = {
    value.asInstanceOf[String]
  }
}