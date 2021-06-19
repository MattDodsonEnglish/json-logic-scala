package com.github.celadari.jsonlogicscala.evaluate

import scala.collection.mutable
import com.github.celadari.jsonlogicscala.evaluate.defaults._
import com.github.celadari.jsonlogicscala.tree.types.TypeValue
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.ObjectRecipe

import scala.jdk.CollectionConverters.MapHasAsScala

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

  def createConf(
                  path: String = "META-INF/services/",
                  operatorsManualAdd: Map[String, MethodConf] = DEFAULT_METHOD_CONFS,
                  evaluatorValueLogicManualAdd: Map[TypeValue, EvaluatorValueLogic] = Map()
                ): EvaluatorLogicConf = {
    val finder = new ResourceFinder(path)
    val propsConf = finder.mapAllProperties(classOf[MethodConf].getName).asScala
    val methodConfsLoaded = propsConf.view.mapValues(prop => {
      val objectRecipe = new ObjectRecipe(propsConf.remove("className").toString)
      objectRecipe.setAllProperties(prop)
      objectRecipe.create().asInstanceOf[MethodConf]
    }).toMap

    val propsEvaluatorValueLogic = finder.mapAllProperties(classOf[EvaluatorValueLogic].getName).asScala
    val evaluatorValueLogicLoaded = propsConf.view.mapValues(prop => {
      val objectRecipe = new ObjectRecipe(propsEvaluatorValueLogic.remove("className").toString)
      objectRecipe.setAllProperties(prop)
      objectRecipe.create().asInstanceOf[EvaluatorValueLogic]
      // TODO Parse and create create TypeValue

    }).toMap.asInstanceOf[Map[TypeValue, EvaluatorValueLogic]]

    EvaluatorLogicConf(methodConfsLoaded ++ operatorsManualAdd, evaluatorValueLogicLoaded ++ evaluatorValueLogicManualAdd)
  }

  implicit val implReduceLogicConf: EvaluatorLogicConf = createConf()

}

case class EvaluatorLogicConf(
                               operatorToMethodConf: Map[String, EvaluatorLogicConf.MethodConf],
                               valueLogicTypeToReducer: Map[TypeValue, EvaluatorValueLogic]
                             )