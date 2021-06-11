package com.github.celadari.jsonlogicscala.serialize

import scala.jdk.CollectionConverters.MapHasAsScala
import com.github.celadari.jsonlogicscala.serialize.defaults._
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.ObjectRecipe

object SerializerConf {
  val DEFAULTS_MARSHALLERS = Map(
    "bool" -> MarshallerBoolean,
    "double" -> MarshallerDouble,
    "float" -> MarshallerFloat,
    "int" -> MarshallerInt,
    "string" -> MarshallerString,
    "long" -> MarshallerLong,
    "byte" -> MarshallerByte,
    "short" -> MarshallerShort
  )

  def createConf(
                  path: String = "META-INF/services/",
                  marshallersClassesManualAdd: Map[String, Marshaller] = DEFAULTS_MARSHALLERS
                ): SerializerConf = {
    val finder = new ResourceFinder("META-INF/services/")
    val props = finder.mapAllProperties(classOf[Marshaller].getName).asScala
    val recipes = props.view.mapValues(prop => {
      val objectRecipe = new ObjectRecipe(prop.remove("className").toString)
      objectRecipe.setAllProperties(prop)
      objectRecipe
    }).toMap
    SerializerConf(recipes, marshallersClassesManualAdd)
  }

  implicit val serializerConf: SerializerConf = createConf()
}

case class SerializerConf(
                           marshallerRecipesToBeAdded: Map[String, ObjectRecipe],
                           marshallersManualAdd: Map[String, Marshaller]
                         )
