package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorCat extends Operator {

  def cat(values: Array[java.lang.String]): java.lang.String = values.mkString
}
