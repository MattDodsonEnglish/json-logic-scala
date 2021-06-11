package com.github.celadari.jsonlogicscala.reduce

import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, TypeValue}

object ReduceLogicConf {

  case class MethodConf(
                         operator: String,
                         methodName: String,
                         pathObjectOpt: Option[String],
                         ownerMethodOpt: Option[Any],
                         paramsType: Seq[Class[_]]
                       ) {
    def isExternalMethod: Boolean = ownerMethodOpt.isDefined
  }

  implicit val implReduceLogicConf: ReduceLogicConf = new ReduceLogicConf("")
}

class ReduceLogicConf(path: String) {
  val operatorToMethodConf: Map[String, ReduceLogicConf.MethodConf] = Map()
  val valueLogicTypeToReducer: Map[TypeValue, Class[_ <: ReducerValueLogic]] = Map()

}
