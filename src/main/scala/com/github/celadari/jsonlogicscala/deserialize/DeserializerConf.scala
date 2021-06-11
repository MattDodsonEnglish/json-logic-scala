package com.github.celadari.jsonlogicscala.deserialize

import scala.jdk.CollectionConverters.MapHasAsScala
import org.apache.xbean.finder.ResourceFinder
import com.github.celadari.jsonlogicscala.deserialize.defaults._

object DeserializerConf {
  val DEFAULTS_UNMARSHALLERS = Map(
    "bool" -> UnmarshallerBoolean,
    "double" -> UnmarshallerDouble,
    "float" -> UnmarshallerFloat,
    "int" -> UnmarshallerInt,
    "string" -> UnmarshallerString,
    "long" -> UnmarshallerLong,
    "byte" -> UnmarshallerByte,
    "short" -> UnmarshallerShort
  )

  def createConf(
                  path: String = "META-INF/services/",
                  unmarshallersClassesManualAdd: Map[String, Unmarshaller] = DEFAULTS_UNMARSHALLERS
                ): DeserializerConf = {
    val finder = new ResourceFinder(path)
    val handler = finder.mapAllImplementations(classOf[Unmarshaller]).asScala
    DeserializerConf(handler.toMap, unmarshallersClassesManualAdd)
  }

  implicit val deserializerConf: DeserializerConf = createConf()

}

case class DeserializerConf(
                             unmarshallerClassesToBeAdded: Map[String, Class[_ <: Unmarshaller]],
                             unmarshallersManualAdd: Map[String, Unmarshaller]
                           )
