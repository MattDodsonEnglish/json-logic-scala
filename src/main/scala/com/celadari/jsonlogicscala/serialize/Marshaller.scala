// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize

import scala.reflect.runtime.{universe => ru}
import play.api.libs.json.JsValue


/**
 * Defines interface of class/object defining marshal method.
 * This method serializes a specific scala data structure to json format.
 */
trait Marshaller {

  /**
   * Serializes a specific scala data structure to json format.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   * @note serialized value of basic types (Int, String, Boolean, ...) are the values themselves, custom defined classes
   * are usually serialized to map of (attribute -> value).
   */
  def marshal(value: Any): JsValue

  // scalastyle:off return
  override def equals(that: Any): Boolean = {
    that match {
      case marshaller: Marshaller => {
        val m = ru.runtimeMirror(getClass.getClassLoader)
        val leftMirror = m.reflect(this)
        val rightMirror = m.reflect(marshaller)
        val leftMembers = leftMirror.symbol.toType.members
        val rightMembers = rightMirror.symbol.toType.members

        val leftVariables = leftMembers.filter(member => member.asTerm.isVal || member.asTerm.isVar).map(sym => {
          val field = leftMirror.reflectField(sym.asTerm)
          (sym.asTerm.fullName, field.get)
        })
        val rightVariables = rightMembers.filter(member => member.asTerm.isVal || member.asTerm.isVar).map(sym => {
          val field = rightMirror.reflectField(sym.asTerm)
          (sym.asTerm.fullName, field.get)
        })
        if (!leftVariables.equals(rightVariables)) return false

        val leftMethods = leftMembers.filter(_.asTerm.isMethod).map(_.asMethod).toList.map(method => {
          (method.fullName, method.paramLists.map(_.map(_.fullName).mkString("[", ", ", "]")).headOption)
        }).sorted
        val rightMethods = rightMembers.filter(_.asTerm.isMethod).map(_.asMethod).toList.map(method => {
          (method.fullName, method.paramLists.map(_.map(_.fullName).mkString("[", ", ", "]")).headOption)
        }).sorted
        if (!leftMethods.equals(rightMethods)) return false

        that.getClass == this.getClass
      }
      case _ => false
    }
  }

  override def hashCode(): Int = {
    val m = ru.runtimeMirror(getClass.getClassLoader)
    val mirror = m.reflect(this)
    val members = mirror.symbol.toType.members

    val variableHashCode = members.filter(member => member.asTerm.isVal || member.asTerm.isVar).map(sym => mirror.reflectField(sym.asTerm).get.hashCode).sum
    val methodHashCode = members.filter(_.asTerm.isMethod).map(_.asMethod).map(method => method.hashCode).sum

    variableHashCode + methodHashCode
  }
}
