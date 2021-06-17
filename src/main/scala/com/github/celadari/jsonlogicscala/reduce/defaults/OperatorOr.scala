package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorOr extends Operator {

  def $bar$bar(bool1: java.lang.Boolean, bool2: java.lang.Boolean): java.lang.Boolean = bool1 || bool2
}