package com.github.celadari.jsonlogicscala.reduce

import scala.reflect.runtime.{universe => ru}

object ReduceLogicConf {

  implicit val implReduceLogicConf: ReduceLogicConf = new ReduceLogicConf("")
}

class ReduceLogicConf(path: String) {
  val operatorToMethodName: Map[String, String] = Map()
  val operatorToMethodParamsType: Map[String, List[List[ru.Type]]] = Map()
  val classNameToReducer: Map[String, Class[_]] = Map()
}
