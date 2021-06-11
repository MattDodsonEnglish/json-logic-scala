package com.github.celadari.jsonlogicscala.reduce

object ReducerValueLogic {
  val identityReducerValueLogic: ReducerValueLogic = new ReducerValueLogic {
    override def reduceValueLogic(value: Any): Any = value
  }
}

trait ReducerValueLogic {

  def reduceValueLogic(value: Any): Any
}
