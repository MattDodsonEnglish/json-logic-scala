package com.github.celadari.jsonlogicscala.evaluate

import scala.jdk.CollectionConverters.MapHasAsScala
import play.api.libs.json.Json
import org.apache.xbean.finder.ResourceFinder
import com.github.celadari.jsonlogicscala.evaluate.defaults._
import com.github.celadari.jsonlogicscala.tree.types.TypeValue
import com.github.celadari.jsonlogicscala.conf.ConfFromPropertiesFile


object EvaluatorLogicConf extends ConfFromPropertiesFile {

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
    "**" -> ("$times$times", OperatorPow),
    "/" -> ("$div", OperatorDiv),
    "%" -> ("$percent", OperatorModulo),
    "^" -> ("$up", OperatorXorBitwise),
    "|" -> ("$bar", OperatorOrBitwise),
    "or" -> ("$bar$bar", OperatorOr),
    "&" -> ("$amp", OperatorAndBitwise),
    "and" -> ("$amp$amp", OperatorAnd),
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
    "substr" -> ("substr", OperatorSubstr),
    "at" -> ("at", OperatorAt)
  )
  val DEFAULT_NONREDUCEOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_NONREDUCEOPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator),
        isReduceTypeOperator = false
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
  val DEFAULT_COMPOSITIONOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_COMPOSITIONOPERATORS_TO_METHODNAME
    .map{case (operator, objOperator) => {
      operator -> MethodConf(
        operator,
        null,
        Some(objOperator),
        isReduceTypeOperator = false,
        isCompositionOperator = true
      )
    }}
  val DEFAULT_UNARYOPERATORS_TO_METHODNAME: Map[String, Operator] = Map(
    "!" -> OperatorNeg,
    "get_or_default_array" -> OperatorOptionGetOrDefaultArray,
    "get_or_default_boolean" -> OperatorOptionGetOrDefaultBoolean,
    "get_or_default_byte" -> OperatorOptionGetOrDefaultByte,
    "get_or_default_short" -> OperatorOptionGetOrDefaultShort,
    "get_or_default_int" -> OperatorOptionGetOrDefaultInt,
    "get_or_default_long" -> OperatorOptionGetOrDefaultLong,
    "get_or_default_float" -> OperatorOptionGetOrDefaultFloat,
    "get_or_default_double" -> OperatorOptionGetOrDefaultDouble,
    "get_or_default_string" -> OperatorOptionGetOrDefaultString,
    "get_or_default_map" -> OperatorOptionGetOrDefaultMap,
    "get_value_or_null" -> OperatorOptionGetValueOrNull
  )
  val DEFAULT_UNARYOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_UNARYOPERATORS_TO_METHODNAME
    .map{case (operator, objOperator) => {
      operator -> MethodConf(
        operator,
        null,
        Some(objOperator),
        isReduceTypeOperator = false,
        isUnaryOperator = true
      )
    }}

  val DEFAULT_METHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCEMETHOD_CONFS ++ DEFAULT_NONREDUCEOPERATORS_CONFS ++ DEFAULT_COMPOSITIONOPERATORS_CONFS ++ DEFAULT_UNARYOPERATORS_CONFS

  case class MethodConf(
                         operator: String,
                         methodName: String,
                         ownerMethodOpt: Option[Operator],
                         isReduceTypeOperator: Boolean = true,
                         isCompositionOperator: Boolean = false,
                         isUnaryOperator: Boolean = false
                       )

  def createConf(
                  pathEvaluatorLogic: String = "META-INF/services/",
                  pathOperator: String = "META-INF/services/",
                  operatorsManualAdd: Map[String, MethodConf] = DEFAULT_METHOD_CONFS,
                  evaluatorValueLogicManualAdd: Map[TypeValue, EvaluatorValueLogic] = Map(),
                  isPriorityToManualAdd: Boolean = true
                ): EvaluatorLogicConf = {

    val finderEvaluatorValueLogic = new ResourceFinder(pathEvaluatorLogic)
    val propsEvaluatorValueLogic = finderEvaluatorValueLogic.mapAllProperties(classOf[EvaluatorValueLogic].getName).asScala
    val evaluatorValueLogicMetaInfTypeNonParsed = propsEvaluatorValueLogic.map{case (fileName, prop) => getOrCreateClassFromProperties[EvaluatorValueLogic](fileName, prop)}.toMap
    val evaluatorValueLogicMetaInf = evaluatorValueLogicMetaInfTypeNonParsed.map{case (typeNonParsed, evaluator) => (Json.toJson(typeNonParsed).as[TypeValue], evaluator)}

    val finderMethodConf = new ResourceFinder(pathOperator)
    val propsMethodConf = finderMethodConf.mapAllProperties(classOf[MethodConf].getName).asScala
    val methodConfMetaInf = propsMethodConf.map{case (fileName, prop) => getOrCreateClassFromProperties[MethodConf](fileName, prop)}.toMap

    EvaluatorLogicConf(methodConfMetaInf, operatorsManualAdd, evaluatorValueLogicMetaInf, evaluatorValueLogicManualAdd, isPriorityToManualAdd)
  }

  implicit val implReduceLogicConf: EvaluatorLogicConf = createConf()
}

case class EvaluatorLogicConf(
                               operatorToMethodConfMetaInfAdd: Map[String, EvaluatorLogicConf.MethodConf],
                               operatorToMethodConfManualAdd: Map[String, EvaluatorLogicConf.MethodConf],
                               valueLogicTypeToReducerMetaInfAdd: Map[TypeValue, EvaluatorValueLogic],
                               valueLogicTypeToReducerManualAdd: Map[TypeValue, EvaluatorValueLogic],
                               isPriorityToManualAdd: Boolean = true
                             ) {

  val operatorToMethodConf: Map[String, EvaluatorLogicConf.MethodConf] = if (isPriorityToManualAdd) operatorToMethodConfMetaInfAdd ++ operatorToMethodConfManualAdd else operatorToMethodConfManualAdd ++ operatorToMethodConfMetaInfAdd
  val valueLogicTypeToReducer: Map[TypeValue, EvaluatorValueLogic] = if (isPriorityToManualAdd) valueLogicTypeToReducerMetaInfAdd ++ valueLogicTypeToReducerManualAdd else valueLogicTypeToReducerManualAdd ++ valueLogicTypeToReducerMetaInfAdd
}