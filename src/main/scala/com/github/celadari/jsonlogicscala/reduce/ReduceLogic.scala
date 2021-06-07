package com.github.celadari.jsonlogicscala.reduce

import com.github.celadari.jsonlogicscala.conversions.TypeConverter
import com.github.celadari.jsonlogicscala.core.{ComposeLogic, JsonLogicCore, ValueLogic}

import scala.reflect.runtime.{universe => ru}

object ReduceLogic {

  implicit val reduceLogic: ReduceLogic = new ReduceLogic
}

class ReduceLogic(implicit val conf: ReduceLogicConf) {
  private[this] val mirror = ru.runtimeMirror(ru.getClass.getClassLoader)

  def reduceComposeLogic(condition: ComposeLogic): Any = {
    val operator = condition.operator
    val methodName = conf.operatorToMethodName(operator)

    val conditionsEval = condition.conditions.map(reduce)

    conditionsEval.reduce[Any]{case (value1, value2) => {
      val im = mirror.reflect(value1)
      val methodSymbs = im.symbol.toType.member(ru.TermName(methodName)).asTerm.alternatives
      val methodSymb = methodSymbs.filter(_.fullName == methodName).find(method => {
        val typeParam = conf.operatorToMethodParamsType(method.fullName).head.head
        TypeConverter.scalaToJavaType(typeParam).isInstance(value2)
      }).get.asMethod
      im.reflectMethod(methodSymb).apply(value2)
    }}
  }

  def reduceValueLogic(condition: ValueLogic[_]): Any = {
    val value = condition.valueOpt.get
    val reducerClass = conf.classNameToReducer(value.getClass.getCanonicalName)
    reducerClass.newInstance().asInstanceOf[ReducerValueLogic].reduceValueLogic(value)
  }

  def reduce(condition: JsonLogicCore): Any = {
    condition match {
      case composeLogic: ComposeLogic => reduceComposeLogic(composeLogic)
      case valueLogic: ValueLogic[_] => reduceValueLogic(valueLogic)
      case other => throw new IllegalArgumentException(s"Invalid argument: $other")
    }

  }

}
