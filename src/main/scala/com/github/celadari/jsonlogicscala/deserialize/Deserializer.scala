package com.github.celadari.jsonlogicscala.deserialize

import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}
import com.github.celadari.jsonlogicscala.deserialize.defaults._
import org.apache.xbean.finder.ResourceFinder
import play.api.libs.json.{JsArray, JsObject, JsValue}

import scala.jdk.CollectionConverters.MapHasAsScala
import scala.collection.mutable

object Deserializer {
  val DEFAULT_UNMARSHALLERS: Map[String, Unmarshaller] = Map(
    "bool" -> UnmarshallerBoolean,
    "double" -> UnmarshallerDouble,
    "float" -> UnmarshallerFloat,
    "int" -> UnmarshallerInt,
    "string" -> UnmarshallerString,
  )

  implicit val defaultDeserializer: Deserializer = new Deserializer(initUnmarshallers = DEFAULT_UNMARSHALLERS)
}

class Deserializer(
                  loadAllUnmarshallersMetaInfAtOnce: Boolean = true,
                  initUnmarshallers: Iterable[(String, Unmarshaller)]=Map()
                  ) {
  private[this] val unmarshallers: mutable.Map[String, Unmarshaller] = mutable.Map()
  unmarshallers ++= initUnmarshallers

  if (loadAllUnmarshallersMetaInfAtOnce) loadUnmarshallersMetaInf()

  private[this] def getUnmarshaller(codename: String): Unmarshaller = {
    if (unmarshallers.contains(codename) || loadAllUnmarshallersMetaInfAtOnce) unmarshallers(codename)
    else{
      val finder = new ResourceFinder("META-INF/services/")
      finder.mapAllImplementations(classOf[Unmarshaller]).get(codename).newInstance()
    }
  }

  private[this] def loadUnmarshallersMetaInf(): Unit = {
    val finder = new ResourceFinder("META-INF/services")
    val handlers = finder.mapAllImplementations(classOf[Unmarshaller]).asScala
    for ((typeData, unmarshallerClass) <- handlers) {
      if (!unmarshallers.contains(typeData)) unmarshallers(typeData) = unmarshallerClass.newInstance()
    }
  }

  private[this] def deserializeValueLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ValueLogic[_] = {
    val codename = (jsonLogic \ "type").as[String]
    val pathData = (jsonLogic \ "var").as[String]
    val jsValue = (jsonLogicData \ pathData).get

    val value = getUnmarshaller(codename).unmarshal(jsValue)

    ValueLogic("var", Some(value))
  }

  private[this] def deserializeComposeLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ComposeLogic = {
    // check for operator field
    val fields = jsonLogic.fields

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
    val operator = fields.head._1

    // if operator is compose logic
    ComposeLogic(operator, deserializeArrayOfConditions((jsonLogic \ operator).get, jsonLogicData))
  }

  private[this] def deserializeArrayOfConditions(json: JsValue, jsonLogicData: JsObject): Array[JsonLogicCore] = {
    val jsArray = json.asInstanceOf[JsArray]
    jsArray
      .value
      .map(jsValue => {
        deserialize(jsValue.asInstanceOf[JsObject], jsonLogicData)
      })
      .toArray
  }

  def deserialize(jsonLogic: JsObject, jsonLogicData: JsObject): JsonLogicCore = {
    // check for operator field
    val fields = jsonLogic.fields

    // if operator is data access
    if (fields.map(_._1).contains("var")) return deserializeValueLogic(jsonLogic, jsonLogicData)

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")

    // if operator is compose logic
    deserializeComposeLogic(jsonLogic, jsonLogicData)
  }

}
