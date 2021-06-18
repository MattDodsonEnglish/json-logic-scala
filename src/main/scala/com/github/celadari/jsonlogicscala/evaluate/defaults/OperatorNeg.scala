package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorNeg extends Operator {

  def unary_$bang(bool1: java.lang.Boolean): java.lang.Boolean = !bool1
}