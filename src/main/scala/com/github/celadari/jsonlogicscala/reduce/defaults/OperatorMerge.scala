package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorMerge extends Operator {

  def merge(values: Array[Array[java.lang.Object]]): java.lang.Object = {
    values.flatten
  }
}
