package com.github.celadari.jsonlogicscala.reduce.defaults

import com.github.celadari.jsonlogicscala.reduce.Operator

object OperatorRightShiftBitwise extends Operator {

  def $greater$greater(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Byte = num1 >> num2
  def $greater$greater(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Short = num1 >> num2
  def $greater$greater(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = num1 >> num2
  def $greater$greater(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Long = num1 >> num2

  def $greater$greater(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Short = num1 >> num2
  def $greater$greater(num1: java.lang.Short, num2: java.lang.Short): java.lang.Short = num1 >> num2
  def $greater$greater(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = num1 >> num2
  def $greater$greater(num1: java.lang.Short, num2: java.lang.Long): java.lang.Long = num1 >> num2

  def $greater$greater(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = num1 >> num2
  def $greater$greater(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = num1 >> num2
  def $greater$greater(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = num1 >> num2
  def $greater$greater(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Long = num1 >> num2

  def $greater$greater(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = num1 >> num2
  def $greater$greater(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = num1 >> num2
  def $greater$greater(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = num1 >> num2
  def $greater$greater(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = num1 >> num2
}