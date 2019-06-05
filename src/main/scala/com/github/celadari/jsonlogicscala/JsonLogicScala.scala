package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.{Decoder, Encoder, JsonLogicCore, ValueLogic}
import play.api.libs.json._



/** The base object for JsonLogicScala */
case object JsonLogicScala {

  object Person {
    implicit def personRead[T](implicit fmt: Reads[T]): Reads[Person[T]] = new Reads[Person[T]] {
      override def reads(json: JsValue): JsResult[Person[T]] = {
        val name = (json \ "name").as[String]
        val kids = (json \ "kids").as[T]
        JsSuccess(Person(name, kids))
      }
    }

    implicit def personWrite[T](implicit fmt: Writes[T]): Writes[Person[T]] = new Writes[Person[T]] {
      override def writes(person: Person[T]): JsValue = {
        JsObject(Map("name" -> Json.toJson(person.name), "kids" -> Json.toJson(person.kids)))
      }
    }
  }

  case class Person[T](name: String, kids: T)
//
  def main(args: Array[String]): Unit = {
    val js = """[{"<": [{"var": "car", "type": "int"}, {"var": "voiture", "type": "long"}, {"var": "jack", "type": "person[Array[Array[Int]]]"}]}, {"jack": {"name": "Jack", "kids": [[5], [6]]}, "car": 2, "voiture": 5}]"""


    implicit val myDecoder: Decoder = new Decoder {
      override def customDecode(json: JsValue, otherType: String)(implicit reads: Array[Reads[_]] = Array()): Any =
        otherType match {
          case "person[Int]" => json.as[Person[Int]]
          case "person[Long]" => json.as[Person[Long]]
          case "person[Array[Int]]" => json.as[Person[Array[Int]]]
          case "person[Array[Array[Int]]]" => json.as[Person[Array[Array[Int]]]]
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
    }

    implicit val myEncoder: Encoder = new Encoder {
      override def customEncode(valueLogic: ValueLogic[_], otherType: String)(implicit writes: Array[Writes[_]]): JsValue = {
        val json = valueLogic.valueOpt.get
        otherType match {
          case "person[Int]" => Json.toJson(json.asInstanceOf[Person[Int]])
          case "person[Long]" => Json.toJson(json.asInstanceOf[Person[Array[Int]]])
          case "person[Array[Int]]" => Json.toJson(json.asInstanceOf[Person[Array[Int]]])
          case "person[Array[Array[Int]]]" => Json.toJson(json.asInstanceOf[Person[Array[Array[Int]]]])
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
      }
    }

    val json = Json.parse(js).as[JsonLogicCore]
    val js2 = """[{"<": [{"var": "voiture", "type": "int"}, {"var": "avion", "type": "int"}]}, {"voiture": 2, "avion": 5}]"""
//    val json2 = Json.parse(js2).as[JsonLogicCore]

    println(js)
    println(Json.stringify(Json.toJson(json)))
//    println(json2.asInstanceOf[ComposeLogic].conditions.mkString("[", ", ", "]"))
//    println(json2.reduce)
//    println(json2)
//    json2.evaluate()
  }

}