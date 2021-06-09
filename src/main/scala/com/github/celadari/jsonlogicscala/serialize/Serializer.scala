package com.github.celadari.jsonlogicscala.serialize

import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}
import com.github.celadari.jsonlogicscala.serialize.defaults.{MarshallerBoolean, MarshallerDouble, MarshallerFloat, MarshallerInt, MarshallerString}
import org.apache.xbean.recipe.ObjectRecipe

import scala.jdk.CollectionConverters.MapHasAsScala
import org.apache.xbean.finder.ResourceFinder
import play.api.libs.json._

import scala.collection.mutable

object Serializer {
  val DEFAULT_MARSHALLERS: Map[String, Marshaller] = Map(
    MarshallerBoolean.className -> MarshallerBoolean,
    MarshallerDouble.className -> MarshallerDouble,
    MarshallerFloat.className -> MarshallerFloat,
    MarshallerInt.className -> MarshallerInt,
    MarshallerString.className -> MarshallerString,
  )

  implicit val defaultSerializer: Serializer = new Serializer(initMarshallers = DEFAULT_MARSHALLERS)
}

class Serializer(
                  loadAllMarshallersMetaInfAtOnce: Boolean = true,
                  initMarshallers: Iterable[(String, Marshaller)]=Map()
                ) {
  private[this] val marshallers: mutable.Map[String, Marshaller] = mutable.Map()
  marshallers ++= initMarshallers

  if (loadAllMarshallersMetaInfAtOnce) loadMarshallersMetaInf()

  private[this] def getMarshaller(className: String): Marshaller = {
    if (!marshallers.contains(className) && !loadAllMarshallersMetaInfAtOnce) {
      val finder = new ResourceFinder("META-INF/services/")
      val prop = finder.mapAllProperties(classOf[Marshaller].getName).get(className)
      val recipe = new ObjectRecipe(prop.remove("className").toString)
      recipe.setAllProperties(prop)
      marshallers(className) = recipe.create().asInstanceOf[Marshaller]
    }
    marshallers(className)
  }

  private[this] def loadMarshallersMetaInf(): Unit = {
    val finder = new ResourceFinder("META-INF/services/")
    val classNameToProperties = finder.mapAllProperties(classOf[Marshaller].getName).asScala
    classNameToProperties
      .filterKeys(className => !marshallers.contains(className))
      .foreach{case (className, prop) => {
      val recipe = new ObjectRecipe(prop.remove("className").toString)
      recipe.setAllProperties(prop)
      marshallers(className) = recipe.create().asInstanceOf[Marshaller]
    }}
    /*val handlers = finder.mapAllImplementations(classOf[Marshaller])
    for ((typeData, marshallerClass) <- handlers) {
      if (!marshallers.contains(typeData)) marshallers(typeData) = marshallerClass.newInstance()
    }*/
  }

  private[this] def serializeValueLogic(valueLogic: ValueLogic[_]): (JsString, JsValue) = {

    val (jsTypeData, jsValue) = valueLogic
      .valueOpt
      .map(value => {
        val marshaller = getMarshaller(value.getClass.getName)
        (JsString(marshaller.codename), marshaller.marshal(value))
      })
      .getOrElse((JsString("null"), JsNull))
    (jsTypeData, jsValue)
  }

  private[this] def serializeComposeLogic(composeLogic: ComposeLogic): (JsValue, JsObject) = {
    // retrieve compose logic attributes
    val operator = composeLogic.operator
    val conditions = composeLogic.conditions

    // create js map operator -> conditions
    val (jsonLogic, jsonLogicData) = serializeArrayOfConditions(conditions)
    (JsObject(Map(operator -> jsonLogic)), jsonLogicData)
  }

  private[this] def serializeArrayOfConditions(conditions: Array[JsonLogicCore]): (JsValue, JsObject) = {
    val (jsonLogics, jsonLogicData) = conditions.map(jsonLogic => serialize(jsonLogic)).unzip
    (JsArray(jsonLogics), jsonLogicData.map(_.as[JsObject]).reduce(_ ++ _))
  }

  def serialize(jsonLogic: JsonLogicCore): (JsValue, JsValue) = {
    // if operator is data access
    jsonLogic match {
      case valueLogic: ValueLogic[_] => serializeValueLogic(valueLogic)
      case composeLogic: ComposeLogic => serializeComposeLogic(composeLogic)
    }
  }
}