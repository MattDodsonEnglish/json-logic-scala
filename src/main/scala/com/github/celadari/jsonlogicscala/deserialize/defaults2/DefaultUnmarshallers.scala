package com.github.celadari.jsonlogicscala.deserialize.defaults2

import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller

import scala.collection.mutable
import scala.reflect.macros.whitebox

object DefaultUnmarshallers {

  val DEFAULT_UNMARSHALLERS: mutable.Set[Unmarshaller] = mutable.Set()

  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    println(444)
//    val result = annottees.map(_.tree).toList match {
//      case q"object $name extends ..$parents { ..$body }" :: Nil =>
//        q"""
//            object $name extends ..$parents {
//            DefaultUnmarshallers.DEFAULT_UNMARSHALLERS.add($name)
//            println(555)
//            def getValue: Int = 5
//            ..$body
//            }
//          """
//    }
//    c.Expr[Any](result)
    annottees(0)
  }
}
