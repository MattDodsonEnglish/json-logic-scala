package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorNeg extends Operator {

  def unary_$bang(bool1: java.lang.Boolean): java.lang.Boolean = !bool1
}