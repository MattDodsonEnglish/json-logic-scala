package com.github.celadari.jsonlogicscala.reduce

import com.github.celadari.jsonlogicscala.tree.types.TypeValue

object ReduceLogicConf {

  val DEFAULT_OPERATORS_TO_METHODNAME: Map[String, String] = Map(
    "<" -> "$less",
    "<=" -> "$less$eq",
    ">" -> "$greater",
    ">=" -> "$greater$eq",
    "<<" -> "$less$less",
    ">>" -> "$greater$greater",
    ">>>" -> "$greater$greater$greater",
    "+" -> "$plus",
    "-" -> "$minus",
    "*" -> "$times",
    "/" -> "$div",
    "%" -> "$percent",
    "^" -> "$up",
    "|" -> "$bar",
    "or" -> "$bar$bar",
    "&" -> "$amp",
    "and" -> "$amp$amp",
    "!" -> "unary_$bang"
  )

  case class MethodConf(
                         operator: String,
                         methodName: String,
                         pathObjectOpt: Option[String],
                         ownerMethodOpt: Option[Operator],
                         paramsType: Seq[Class[_]],
                         isReduceType: Boolean = true
                       ) {
    def isExternalMethod: Boolean = ownerMethodOpt.isDefined
  }

  implicit val implReduceLogicConf: ReduceLogicConf = new ReduceLogicConf("")
}

class ReduceLogicConf(path: String) {
  val operatorToMethodConf: Map[String, ReduceLogicConf.MethodConf] = Map()
  val valueLogicTypeToReducer: Map[TypeValue, Class[_ <: ReducerValueLogic]] = Map()

}
