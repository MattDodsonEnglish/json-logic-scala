package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorMerge extends Operator {

  def merge(values: Array[Array[java.lang.Object]]): java.lang.Object = {
    values.flatten
  }
}
