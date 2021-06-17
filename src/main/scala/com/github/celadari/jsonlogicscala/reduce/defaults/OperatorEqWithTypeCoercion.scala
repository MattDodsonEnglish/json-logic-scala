package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorEqWithTypeCoercion extends Operator {

  def $eq$eq(value1: java.lang.Object, value2: java.lang.Object): java.lang.Boolean = value1 == value2
}