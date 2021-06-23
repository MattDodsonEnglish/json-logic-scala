package com.github.celadari.jsonlogicscala.exceptions

import com.github.celadari.jsonlogicscala.tree.JsonLogicCore

final class WrongInputCondition(msg: String, val conditionOpt: Option[JsonLogicCore] = None)
  extends JsonLogicScalaException(msg)