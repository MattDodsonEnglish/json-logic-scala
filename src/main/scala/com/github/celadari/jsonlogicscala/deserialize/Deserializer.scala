package com.github.celadari.jsonlogicscala.deserialize

import scala.collection.mutable
import play.api.libs.json.{JsArray, JsLookupResult, JsNull, JsObject, JsValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}
import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, SimpleTypeValue, TypeValue}
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException

object Deserializer {

  def unmarshType(jsLookupResult: JsLookupResult): Option[TypeValue] = {
    if (jsLookupResult.isDefined) {
      try {
        Some(jsLookupResult.get.as[TypeValue])
      }
      catch {
        case _: Throwable => throw new InvalidJsonParsingException(s"Invalid typevalue json format: ${jsLookupResult.get.toString}")
      }
    }
    else None
  }
  implicit val defaultDeserializer: Deserializer = new Deserializer()
}

class Deserializer(implicit val conf: DeserializerConf) {

  protected[this] val unmarshallers: mutable.Map[String, Unmarshaller] = mutable.Map[String, Unmarshaller]() ++ conf.unmarshallersManualAdd

  protected[this] def getUnmarshaller(typeValue: TypeValue): Unmarshaller = {
    typeValue match {
      case SimpleTypeValue(codename) => unmarshallers.getOrElseUpdate(codename, conf.unmarshallerClassesToBeAdded(codename).newInstance())
      case ArrayTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = jsValue.as[JsArray].value.toArray.map(jsValue => getUnmarshaller(paramType).unmarshal(jsValue))
      }
      case MapTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = jsValue.as[JsObject].value.view.mapValues(jsValue => getUnmarshaller(paramType).unmarshal(jsValue)).toMap
      }
      case AnyTypeValue => throw new IllegalArgumentException("Cannot serialize JsonLogicCore object " +
        "with type AnyTypeValue. \nAnyTypeValue is used at evaluation for composition operators")
    }
  }

  protected[this] def deserializeValueLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ValueLogic[_] = {
    val isTypeDefined = (jsonLogic \ "type").isDefined
    val typeValueOpt = Deserializer.unmarshType((jsonLogic \ "type"))
    val pathData = (jsonLogic \ "var").as[String]
    val lookUpPathData = (jsonLogicData \ pathData)
    val jsValue = lookUpPathData.getOrElse(JsNull)

    if (isTypeDefined && lookUpPathData.isEmpty) throw new InvalidJsonParsingException("Error while parsing ValueLogic of type value: \"var\" path is undefined")
    if (!isTypeDefined && lookUpPathData.isDefined) throw new InvalidJsonParsingException("Error while parsing ValueLogic of type variable: \"var\" must not be a key on data dictionary")

    val valueOpt = typeValueOpt.flatMap(typeValue => Option(getUnmarshaller(typeValue).unmarshal(jsValue)))
    val variableNameOpt = if (lookUpPathData.isDefined) None else Some(pathData)
    val pathDataOpt = if (lookUpPathData.isDefined) Some(pathData) else None

    ValueLogic(valueOpt, typeValueOpt, variableNameOpt, pathDataOpt)
  }

  protected[this] def deserializeComposeLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ComposeLogic = {
    // check for operator field
    val fields = jsonLogic.fields

    // check for compose logic operator field
    if (fields.length > 1) {
      throw new InvalidJsonParsingException(s"ComposeLogic cannot have more than one operator field." +
        s"\nCurrent operators: ${fields.map(_._1).mkString("[", ", ", "]")}" +
        s"\nInvalid ComposeLogic json: ${jsonLogic.toString}")
    }
    if (fields.isEmpty) throw new InvalidJsonParsingException("ComposeLogic cannot be empty")
    val operator = fields.head._1

    // if operator is compose logic
    new ComposeLogic(operator, deserializeArrayOfConditions((jsonLogic \ operator).get, jsonLogicData))
  }

  protected[this] def deserializeArrayOfConditions(json: JsValue, jsonLogicData: JsObject): Array[JsonLogicCore] = {
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

    // if operator is compose logic
    deserializeComposeLogic(jsonLogic, jsonLogicData)
  }

}
