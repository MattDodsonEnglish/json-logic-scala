package com.github.celadari.jsonlogicscala.reduce

import scala.collection.mutable
import com.github.celadari.jsonlogicscala.reduce.defaults._
import com.github.celadari.jsonlogicscala.tree.types.TypeValue

object ReduceLogicConf {

  val DEFAULT_OPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
    "<" -> ("$less", OperatorLess),
    "<=" -> ("$less$eq", OperatorLessEq),
    ">" -> ("$greater", OperatorGreater),
    ">=" -> ("$greater$eq", OperatorGreaterEq),
    //"<<" -> ("$less$less", OperatorLessLess),
    //">>" -> ("$greater$greater", OperatorGreaterGreater),
    //">>>" -> ("$greater$greater$greater", OperatorGreaterGreaterGreater),
    "+" -> ("$plus", OperatorPlus),
    "-" -> ("$minus", OperatorMinus),
    "*" -> ("$times", OperatorTimes),
    "/" -> ("$div", OperatorDiv),
    "%" -> ("$percent", OperatorModulo),
    "^" -> ("$up", OperatorXor)
    //"|" -> ("$bar", OperatorBar),
    //"or" -> ("$bar$bar", OperatorOr),
    //"&" -> ("$amp", OperatorAmp),
    //"and" -> ("$amp$amp", OperatorAnd),
    //"!" -> ("unary_$bang", OperatorNeg)
  )
  val DEFAULT_METHOD_CONFS: Map[String, MethodConf] = DEFAULT_OPERATORS_TO_METHODNAME.map{case (operator, (methodName, objOperator)) => {
    operator -> MethodConf(
      operator,
      methodName,
      Some(objOperator)
    )
  }}

  case class MethodConf(
                         operator: String,
                         methodName: String,
                         ownerMethodOpt: Option[Operator],
                         isReduceType: Boolean = true
                       ) {
    def isExternalMethod: Boolean = ownerMethodOpt.isDefined
  }

  implicit val implReduceLogicConf: ReduceLogicConf = new ReduceLogicConf("") {
    _operatorToMethodConf ++= DEFAULT_METHOD_CONFS
  }
}

class ReduceLogicConf(path: String) {
  protected[this] val _operatorToMethodConf: mutable.Map[String, ReduceLogicConf.MethodConf] = mutable.Map()
  protected[this] val _valueLogicTypeToReducer: mutable.Map[TypeValue, Class[_ <: ReducerValueLogic]] = mutable.Map()

  def operatorToMethodConf: Map[String, ReduceLogicConf.MethodConf] = _operatorToMethodConf.toMap
  def valueLogicTypeToReducer: Map[TypeValue, Class[_ <: ReducerValueLogic]] = _valueLogicTypeToReducer.toMap

}
