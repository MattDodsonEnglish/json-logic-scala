package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorGreater extends Operator {

  def $greater(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Byte, num2: java.lang.Float): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Byte, num2: java.lang.Double): java.lang.Boolean = num1 > num2

  def $greater(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Short, num2: java.lang.Short): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Short, num2: java.lang.Long): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Short, num2: java.lang.Float): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Short, num2: java.lang.Double): java.lang.Boolean = num1 > num2

  def $greater(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Integer, num2: java.lang.Float): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Integer, num2: java.lang.Double): java.lang.Boolean = num1 > num2

  def $greater(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Long, num2: java.lang.Short): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Long, num2: java.lang.Long): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Long, num2: java.lang.Float): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Long, num2: java.lang.Double): java.lang.Boolean = num1 >  num2

  def $greater(num1: java.lang.Float, num2: java.lang.Byte): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Float, num2: java.lang.Short): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Float, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Float, num2: java.lang.Long): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Float, num2: java.lang.Float): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Float, num2: java.lang.Double): java.lang.Boolean = num1 > num2

  def $greater(num1: java.lang.Double, num2: java.lang.Byte): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Double, num2: java.lang.Short): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Double, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Double, num2: java.lang.Long): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Double, num2: java.lang.Float): java.lang.Boolean = num1 > num2
  def $greater(num1: java.lang.Double, num2: java.lang.Double): java.lang.Boolean = num1 > num2

}