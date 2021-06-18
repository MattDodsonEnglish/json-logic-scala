package com.github.celadari.jsonlogicscala.deserialize.defaults

import com.github.celadari.jsonlogicscala.evaluate.defaults.OperatorPlus
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.reflect.runtime.{universe => ru}
import ru._
import play.api.libs.json._



class TestDefaultUnmarshallerBoolean extends AnyFlatSpec with Matchers {

  val js = JsObject(Map("sdf" -> JsString("voiture")))

  println("sdfdsf")

  class Parent
  trait Family
  class Child extends Parent with Family

  def trySth(a: Array[Parent]): Any = {
    println("parent")
    a
  }
  def trySth(c: Array[Child]): Any = {
    println("child")
    c
  }
  def trySth(b: Array[Family]): Any = {
    println("family")
    b
  }

  val parent = new Parent
  val child = new Child
  val fam: Family = new Family{}

  println(trySth(Array(parent)))
  println(trySth(Array(fam)))
  println(trySth(Array(child)))

  class D
  new A + 5
  OperatorPlus.$plus(Int.box(4), Int.box(5))
  import scala.reflect.runtime.{currentMirror => m}
  val x: java.lang.Integer = 4
  val y: java.lang.Integer = 4
  x + y
  val tree = Apply(Select(Ident(TermName("x")), TermName("$plus")), List(Literal(Constant(2))))
  println(tree)
  println(5.getClass.getMethods.map(_.getName).toSeq)

  "Boolean marshaller" should "marshal to boolean" in {

    def getProperty(obj: Any, obj2: Any, property2: String): Unit = {
      val mirror = ru.runtimeMirror(ru.getClass.getClassLoader)
      val mp = mirror.reflectModule(mirror.staticModule("com.github.celadari.jsonlogicscala.deserialize.defaults.SomeObject"))
      println(mp.instance)


      val m = ru.runtimeMirror(ru.getClass.getClassLoader)
      val im = m.reflect(obj)

      val property = "$plus"
      val symb = im.symbol.toType.member(ru.TermName(property)).asTerm.alternatives
      println(symb)
      symb.foreach(sy => {
        println(sy.name)
        println(sy.asMethod)
        println(sy.asMethod.typeSignature)
        //println(sy.asMethod.paramLists.map(_.map(_.typeSignature)))

        println("AAAA")
        //println(im.symbol.typeSignature)
        //println(symb.asMethod.typeParams)
//        println(symb.typeSignature)
      })
      val callable = symb.head.asMethod
      //val callable = symb.find(sy => sy.asMethod.paramLists(0)(0).typeSignature =:= ru.typeOf[Int]).get.asMethod
      val x = 4.asInstanceOf[Any]
      //println(im.reflectMethod(callable).apply(x))
      //println(im.reflectMethod(symb).apply(5))

      ///////////////////// OBJ2 START
      val yy: Any = 5
      val im2 = m.reflect(yy)
      //println(im2.symbol.info)
      println("DDDDDDDDDDDDDDDD")
      println(im2.symbol.toType.typeConstructor.typeSymbol)
      println(classOf[Int])
      //println(m.runtimeClass(im2.symbol.toType.typeConstructor.typeSymbol.asClass))
      //println(im2.symbol.toType.typeConstructor.typeSymbol.asClass)
      //println(im2.symbol.asType)

      ///////////////////// OBJ2 END
      val classObj2 = obj2.getClass
      classObj2.isInstance()


      obj.getClass.getMethods.filter(_.getName == property).foreach(m => {
        //println(m)
        val me = m.getGenericParameterTypes()
        if (me.size == 1 /*&& me.head == classObj2*/) {
          val methodParams = me.head
          //println(me.head)
          //println(me.head.asInstanceOf[Class[_]])
          //println(classObj2)
          //println("FOUND")
        }
        //println(me.toSeq)
      })
      val a = new A
      val imA = m.reflect(a)

      val b = new B
      SomeObject.letsDoIt(b)

      ru.TermName(property)
      //val fld = im.reflectField(symb)
      //fld.get
    }


    val c = (new C).asInstanceOf[Any]
    val e = 4
    //getProperty(c, e, "$plus")
    //getProperty(4, 5, "+")

    val c2 = new C
    println(c2 + 4L)

  }



}
