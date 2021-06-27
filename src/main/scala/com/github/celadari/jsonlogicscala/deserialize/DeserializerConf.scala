package com.github.celadari.jsonlogicscala.deserialize

import scala.jdk.CollectionConverters.MapHasAsScala
import org.apache.xbean.finder.ResourceFinder
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.deserialize.defaults._

object DeserializerConf {
  val DEFAULTS_UNMARSHALLERS = Map(
    BOOL_CODENAME -> UnmarshallerBoolean,
    DOUBLE_CODENAME -> UnmarshallerDouble,
    FLOAT_CODENAME -> UnmarshallerFloat,
    INT_CODENAME -> UnmarshallerInt,
    STRING_CODENAME -> UnmarshallerString,
    LONG_CODENAME -> UnmarshallerLong,
    BYTE_CODENAME -> UnmarshallerByte,
    SHORT_CODENAME -> UnmarshallerShort,
    NULL_CODENAME -> UnmarshallerNull
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
