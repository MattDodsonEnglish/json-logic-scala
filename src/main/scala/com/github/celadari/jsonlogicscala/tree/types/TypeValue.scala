package com.github.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsError, JsObject, JsResult, JsString, JsSuccess, JsValue, Reads, Writes}

object TypeValue {

  private[this] def parseTypeValue(json: JsValue): TypeValue = {
    val codename = (json \ "codename").as[String]
    val paramType = (json \ "paramType").asOpt[JsValue]

    paramType
      .map(jsParamType => codename match {
        case ArrayTypeValue.CODENAME_TYPE => ArrayTypeValue(parseTypeValue(jsParamType))
        case MapTypeValue.CODENAME_TYPE => MapTypeValue(parseTypeValue(jsParamType))
        case TypeVariable.CODENAME_TYPE => TypeVariable
        case _ => throw new IllegalArgumentException("Wrong type")
      })
      .getOrElse(SimpleTypeValue(codename))
  }

  implicit val readTypeValue: Reads[TypeValue] = new Reads[TypeValue] {
    override def reads(json: JsValue): JsResult[TypeValue] = {
      try {
        JsSuccess(parseTypeValue(json))
      }
      catch {
        case _: Throwable => JsError("Wrong json format for type value")
      }
    }
  }

  private[this] def serializeTypeValue(typeValue: TypeValue): JsValue = {
    typeValue match {
      case SimpleTypeValue(codename) => JsObject(Map("codename" -> JsString(codename)))
      case ArrayTypeValue(paramType) => JsObject(Map(
        "codename" -> JsString(ArrayTypeValue.CODENAME_TYPE),
        "paramType" -> serializeTypeValue(paramType)
      ))
      case MapTypeValue(paramType) => JsObject(Map(
        "codename" -> JsString(MapTypeValue.CODENAME_TYPE),
        "paramType" -> serializeTypeValue(paramType)
      ))
      case TypeVariable(variableName) => JsObject(Map(
        "codename" -> JsString(TypeVariable.CODENAME_TYPE)
      ))
      case _ => throw new IllegalArgumentException("Wrong type for type value")
    }
  }

  implicit val writesTypeValue: Writes[TypeValue] = new Writes[TypeValue] {
    override def writes(o: TypeValue): JsValue = serializeTypeValue(o)
  }

}

abstract class TypeValue(val codename: String)
