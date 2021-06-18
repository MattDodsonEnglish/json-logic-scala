package com.github.celadari.jsonlogicscala.evaluate

import scala.collection.mutable
import com.github.celadari.jsonlogicscala.evaluate.defaults._
import com.github.celadari.jsonlogicscala.tree.types.TypeValue

object EvaluatorLogicConf {

  val DEFAULT_REDUCEOPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
    "<" -> ("$less", OperatorLess),
    "<=" -> ("$less$eq", OperatorLessEq),
    ">" -> ("$greater", OperatorGreater),
    ">=" -> ("$greater$eq", OperatorGreaterEq),
    "<<" -> ("$less$less", OperatorLeftShiftBitwise),
    ">>" -> ("$greater$greater", OperatorRightShiftBitwise),
    ">>>" -> ("$greater$greater$greater", OperatorRightShiftZeroFillerBitwise),
    "+" -> ("$plus", OperatorPlus),
    "-" -> ("$minus", OperatorMinus),
    "*" -> ("$times", OperatorTimes),
    "/" -> ("$div", OperatorDiv),
    "%" -> ("$percent", OperatorModulo),
    "^" -> ("$up", OperatorXor),
    "|" -> ("$bar", OperatorOrBitwise),
    "or" -> ("$bar$bar", OperatorOr),
    "&" -> ("$amp", OperatorAndBitwise),
    "and" -> ("$amp$amp", OperatorAnd),
    "!" -> ("unary_$bang", OperatorNeg),
    "==" -> ("$eq$eq", OperatorEqWithTypeCoercion),
    "===" -> ("$eq$eq$eq", OperatorEqStrict),
    "!=" -> ("$bang$eq", OperatorNEqWithTypeCoercion),
    "!==" -> ("$bang$eq$eq", OperatorNEqStrict),
    "max" -> ("max", OperatorMax),
    "min" -> ("min", OperatorMin)
  )
  val DEFAULT_REDUCEMETHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCEOPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator)
      )
    }}

  val DEFAULT_NONREDUCEOPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
    "if" -> ("ifElse", OperatorIfElse),
    "in" -> ("in", OperatorIn),
    "merge" -> ("merge", OperatorMerge),
    "cat" -> ("cat", OperatorCat),
    "substr" -> ("substr", OperatorSubstr)
  )
  val DEFAULT_NONREDUCEOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_NONREDUCEOPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator),
        isReduceType = false
      )
    }}

  val DEFAULT_COMPOSITIONOPERATORS_TO_METHODNAME: Map[String, Operator] = Map(
    "map" -> OperatorMap,
    "reduce" -> OperatorReduce,
    "filter" -> OperatorFilter,
    "all" -> OperatorAll,
    "none" -> OperatorNone,
    "some" -> OperatorSome
  )
  val DEFAULT_COMPOSITIONOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_NONREDUCEOPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator),
        isReduceType = false
      )
    }}

  val DEFAULT_METHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCEMETHOD_CONFS ++ DEFAULT_NONREDUCEOPERATORS_CONFS ++ DEFAULT_COMPOSITIONOPERATORS_CONFS

  case class MethodConf(
                         operator: String,
                         methodName: String,
                         ownerMethodOpt: Option[Operator],
                         isReduceType: Boolean = true,
                         isCompositionOperator: Boolean = false
                       ) {
    def isExternalMethod: Boolean = ownerMethodOpt.isDefined
  }

  implicit val implReduceLogicConf: EvaluatorLogicConf = new EvaluatorLogicConf("") {
    _operatorToMethodConf ++= DEFAULT_METHOD_CONFS
  }
}

class EvaluatorLogicConf(path: String) {
  protected[this] val _operatorToMethodConf: mutable.Map[String, EvaluatorLogicConf.MethodConf] = mutable.Map()
  protected[this] val _valueLogicTypeToReducer: mutable.Map[TypeValue, Class[_ <: EvaluatorValueLogic]] = mutable.Map()

  def operatorToMethodConf: Map[String, EvaluatorLogicConf.MethodConf] = _operatorToMethodConf.toMap
  def valueLogicTypeToReducer: Map[TypeValue, Class[_ <: EvaluatorValueLogic]] = _valueLogicTypeToReducer.toMap

}
