package com.github.celadari.jsonlogicscala.deserialize

import scala.collection.mutable
import play.api.libs.json.{JsArray, JsNull, JsObject, JsValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}
import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, SimpleTypeValue, TypeValue}


object Deserializer {
  implicit val defaultDeserializer: Deserializer = new Deserializer()
}

class Deserializer(implicit val conf: DeserializerConf) {

  private[this] val unmarshallers = mutable.Map[String, Unmarshaller]() ++ conf.unmarshallersManualAdd

  private[this] def getUnmarshaller(typeValue: TypeValue): Unmarshaller = {
    typeValue match {
      case SimpleTypeValue(codename) => unmarshallers.getOrElseUpdate(codename, conf.unmarshallerClassesToBeAdded(codename).newInstance())
      case ArrayTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = jsValue.as[JsArray].value.toArray.map(jsValue => getUnmarshaller(paramType).unmarshal(jsValue))
      }
      case MapTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = jsValue.as[JsObject].value.view.mapValues(jsValue => getUnmarshaller(paramType).unmarshal(jsValue))
      }
      case AnyTypeValue => throw new IllegalArgumentException("Cannot serialize JsonLogicCore object " +
        "with type AnyTypeValue. \nAnyTypeValue is used at evaluation for composition operators")
    }
  }

  private[this] def deserializeValueLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ValueLogic[_] = {
    val typeValueOpt = (jsonLogic \ "type").asOpt[TypeValue]
    val pathData = (jsonLogic \ "var").as[String]
    val jsValue = (jsonLogicData \ pathData).getOrElse(JsNull)

    val valueOpt = typeValueOpt.map(typeValue => getUnmarshaller(typeValue).unmarshal(jsValue))
    val variableNameOpt = if ((jsonLogicData \ pathData).isDefined) None else Some(pathData)

    ValueLogic(valueOpt, typeValueOpt, variableNameOpt)
  }

  private[this] def deserializeComposeLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ComposeLogic = {
    // check for operator field
    val fields = jsonLogic.fields

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
    val operator = fields.head._1

    // if operator is compose logic
    new ComposeLogic(operator, deserializeArrayOfConditions((jsonLogic \ operator).get, jsonLogicData))
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
