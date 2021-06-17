package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorNEqWithTypeCoercion extends Operator {

  def $bang$eq(value1: java.lang.Object, value2: java.lang.Object): java.lang.Boolean = value1 != value2
}