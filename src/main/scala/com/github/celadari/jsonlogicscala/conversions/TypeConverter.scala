package com.github.celadari.jsonlogicscala.conversions

import scala.reflect.runtime.{universe => ru}

object TypeConverter {

  private[this] val defaultMirror = ru.runtimeMirror(getClass.getClassLoader)

  val mapScalaToJavaType: Map[ru.Type, Class[_]] = Map(
    ru.typeOf[Double] -> classOf[java.lang.Double],
    ru.typeOf[Long] -> classOf[java.lang.Long],
    ru.typeOf[Int] -> classOf[java.lang.Integer],
    ru.typeOf[Char] -> classOf[java.lang.Character],
    ru.typeOf[Float] -> classOf[java.lang.Float],
    ru.typeOf[Byte] -> classOf[java.lang.Byte]
  )

  def scalaToJavaType(tpe: ru.Type): Class[_] = {
    mapScalaToJavaType.getOrElse(tpe, defaultMirror.runtimeClass(tpe.typeConstructor.typeSymbol.asClass))
  }
}
