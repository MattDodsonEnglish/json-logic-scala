package com.github.celadari.jsonlogicscala.tree

import play.api.libs.json._
import com.github.celadari.jsonlogicscala.deserialize.Deserializer
import com.github.celadari.jsonlogicscala.serialize.Serializer

object JsonLogicCore {

  implicit def jsonLogicCoreReads(implicit deserializer: Deserializer): Reads[JsonLogicCore] = new Reads[JsonLogicCore] {

    override def reads(json: JsValue): JsResult[JsonLogicCore] = {

      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = (json \ 1).getOrElse(JsObject.empty).asInstanceOf[JsObject]

      // apply reading with distinguished components: logic and data
      JsSuccess(deserializer.deserialize(jsonLogic, jsonLogicData))
    }
  }

  implicit def jsonLogicCoreWrites(implicit serializer: Serializer): Writes[JsonLogicCore] = new Writes[JsonLogicCore] {

    override def writes(jsonLogicCore: JsonLogicCore): JsValue = {
      // apply writing
      val (jsonLogic, jsonLogicData) = serializer.serialize(jsonLogicCore)

      // return final result
      JsArray(Array(jsonLogic, jsonLogicData))
    }
  }
}


abstract class JsonLogicCore(val operator: String)