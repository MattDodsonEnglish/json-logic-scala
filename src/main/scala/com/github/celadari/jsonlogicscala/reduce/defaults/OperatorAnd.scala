package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorAnd extends Operator {

  def $amp$amp(bool1: java.lang.Boolean, bool2: java.lang.Boolean): java.lang.Boolean = bool1 && bool2
}