package com.github.celadari.jsonlogicscala.exceptions

import com.github.celadari.jsonlogicscala.tree.JsonLogicCore

final class EvaluationException(msg: String, val condition: JsonLogicCore, val origException: Throwable)
  extends JsonLogicScalaException(msg)
