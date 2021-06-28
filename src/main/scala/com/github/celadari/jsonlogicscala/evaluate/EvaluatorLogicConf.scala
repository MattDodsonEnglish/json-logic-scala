package com.github.celadari.jsonlogicscala.evaluate

import scala.jdk.CollectionConverters.MapHasAsScala
import com.github.celadari.jsonlogicscala.evaluate.defaults._
import com.github.celadari.jsonlogicscala.tree.types.TypeValue
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.ObjectRecipe

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