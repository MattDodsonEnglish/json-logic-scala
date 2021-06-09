package com.github.celadari.jsonlogicscala.reduce

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
  val classNameToReducer: Map[String, Class[_]] = Map()

}
