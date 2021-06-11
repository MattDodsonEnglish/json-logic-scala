package com.github.celadari.jsonlogicscala.deserialize.defaults


trait Sword {

}



class A {

  //def +(u: Long): Long = u + 45
  def +(u: Int): Int = u + 6
  def doSth(u: Int): Int = u + 5
  def doSth(u: Long): Long = u + 4L
}

class B extends A {

  def +(u: Float): Float = u + 96
  override def +(u: Int): Int = u + 5
  override def doSth(u: Int): Int = u + 13
  //override def doSth(u: Long): Long = u + 45L
  def manageDo(y: String): String = y ++ "voiture"

  def doNone[T](so: T): T = so
  def doNone2[T <: Number](num: T): T = num
}

class C extends B {

  def +(u: String): String = u + "YOLO"
  override def doSth(u: Int): Int = u + 67
  def manageDo2(y: String): String = y ++ "avion"

  def doNone3[T](so: T): T = so
  def doNone4[T <: Sword](num: T): T = num
}

object SomeObject {
  def letsDoIt(a: A): Unit = println("rrtyy")
  def letsDoIt(b: B): Unit = println("dfgfdg")
}