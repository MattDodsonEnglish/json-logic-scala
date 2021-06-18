package com.github.celadari.jsonlogicscala.serialize

import scala.collection.mutable
import java.util.UUID
import play.api.libs.json._
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}
import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, SimpleTypeValue, TypeValue}


object Serializer {
  implicit val defaultSerializer: Serializer = new Serializer()
}

class Serializer(implicit val conf: SerializerConf) {
  private[this] val marshallers: mutable.Map[String, Marshaller] = mutable.Map[String, Marshaller]() ++ conf.marshallersManualAdd

  private[this] def getMarshaller(typeValue: TypeValue): Marshaller = {
    typeValue match {
      case SimpleTypeValue(codename) => marshallers.getOrElseUpdate(codename, conf.marshallerRecipesToBeAdded(codename).create().asInstanceOf[Marshaller])
      case ArrayTypeValue(paramType) => new Marshaller {
        override val typeCodename: String = null
        override val typeClassName: String = null
        override def marshal(value: Any): JsValue = JsArray(value.asInstanceOf[Array[Any]].map(el => getMarshaller(paramType).marshal(el)))
      }
      case MapTypeValue(paramType) => new Marshaller {
        override val typeCodename: String = null
        override val typeClassName: String = null

        override def marshal(value: Any): JsValue = JsObject(value.asInstanceOf[Map[String, Any]].view.mapValues(el => getMarshaller(paramType).marshal(el)).toMap)
      }
      case AnyTypeValue => throw new IllegalArgumentException("Cannot serialize JsonLogicCore object " +
        "with type AnyTypeValue. \nAnyTypeValue is used at evaluation for composition operators")
      case _ => throw new IllegalArgumentException("Wrong argument type value")
    }
  }

  private[this] def serializeValueLogic(valueLogic: ValueLogic[_]): (JsValue, JsValue) = {

    val (jsType, jsonLogicDatum) = valueLogic
      .valueOpt
      .map(value => {
        val typeCodenameOpt = valueLogic.typeCodenameOpt
        if (typeCodenameOpt.isEmpty) return (JsString("null"), JsNull)
        val marshaller = getMarshaller(typeCodenameOpt.get)
        (Json.toJson(typeCodenameOpt.get), marshaller.marshal(value))
      })
      .getOrElse((JsString("null"), JsNull))

    val uuid = UUID.randomUUID.toString
    (JsObject(Map("var" -> JsString(uuid), "type" -> jsType)), JsObject(Map(uuid -> jsonLogicDatum)))
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