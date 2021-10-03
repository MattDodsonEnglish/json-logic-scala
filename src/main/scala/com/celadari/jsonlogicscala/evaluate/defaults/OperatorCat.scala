// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


object OperatorCat extends Operator {

  def cat(values: Array[Any]): java.lang.String = values.mkString
}
