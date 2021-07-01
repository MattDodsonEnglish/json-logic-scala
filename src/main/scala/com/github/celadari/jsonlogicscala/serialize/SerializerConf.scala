package com.github.celadari.jsonlogicscala.serialize

import scala.jdk.CollectionConverters.MapHasAsScala
import scala.reflect.runtime.{universe => ru}
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.{MissingAccessorException, ObjectRecipe}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.serialize.defaults._
import com.github.celadari.jsonlogicscala.exceptions.ConfigurationException

import java.util.Properties

object SerializerConf {
  val DEFAULT_MARSHALLERS = Map(
    BOOL_CODENAME -> MarshallerBoolean,
    DOUBLE_CODENAME -> MarshallerDouble,
    FLOAT_CODENAME -> MarshallerFloat,
    INT_CODENAME -> MarshallerInt,
    STRING_CODENAME -> MarshallerString,
    LONG_CODENAME -> MarshallerLong,
    BYTE_CODENAME -> MarshallerByte,
    SHORT_CODENAME -> MarshallerShort,
    NULL_CODENAME -> MarshallerNull
  )

  def getOrCreateMarshaller(fileName: String, prop: Properties): (String, Marshaller) = {
    if (!prop.containsKey("className")) throw new ConfigurationException(s"Property file '$fileName' must define key 'className'")
    if (!prop.containsKey("codename")) throw new ConfigurationException(s"Property file '$fileName' must define key 'codename'")
    val className = prop.remove("className").toString
    val typeCodename = prop.remove("codename").toString

    val isObject = if (prop.containsKey("singleton")) prop.remove("singleton").toString.toBoolean else false
    if (isObject) {
      val m = ru.runtimeMirror(getClass.getClassLoader)
      try {
        (typeCodename, m.reflectModule(m.staticModule(className)).instance.asInstanceOf[Marshaller])
      }
      catch {
        case castException: java.lang.ClassCastException => throw new ConfigurationException(s"Found object is not a Marshaller instance: \n${castException.getMessage}")
        case _: Throwable => throw new ConfigurationException(s"No singleton object found for: $className\nCheck if 'className' '$className' is correct and if 'singleton' property in '$fileName' property file is correct")
      }
    }
    else {
      try {
        val objectRecipe = new ObjectRecipe(className)
        val sep = if (prop.containsKey("sep")) prop.remove("sep").toString else ";"
        if (prop.containsKey("constructorArgNames")) objectRecipe.setConstructorArgNames(prop.remove("constructorArgNames").toString.split(sep))
        objectRecipe.setAllProperties(prop)
        (typeCodename, objectRecipe.create().asInstanceOf[Marshaller])
      }
      catch {
        case castException: java.lang.ClassCastException => throw new ConfigurationException(s"Found class is not a Marshaller instance: \n${castException.getMessage}")
        case _: MissingAccessorException => throw new ConfigurationException(s"Field error, check that no field in '$className' is missing in '$fileName' property file.\nCheck that no property in '$fileName' file is not undefined in '$className' class.\nCheck if '$className' class constructor requires arguments or if argument names defined in '$fileName' property file are correct")
        case _: Throwable => throw new ConfigurationException(s"No class found for: $className\nCheck if 'className' '$className' is correct and if 'singleton' property in '$fileName' property file is correct")
      }
    }
  }

  def createConf(
                  path: String = "META-INF/services/",
                  marshallersClassesManualAdd: Map[String, Marshaller] = DEFAULT_MARSHALLERS,
                  isPriorityToManualAdd: Boolean = true
                ): SerializerConf = {
    val finder = new ResourceFinder(path)
    val props = finder.mapAllProperties(classOf[Marshaller].getName).asScala
    val marshallersMetaInf = props.map{case (fileName, prop) => getOrCreateMarshaller(fileName, prop)}.toMap
    SerializerConf(marshallersMetaInf, marshallersClassesManualAdd, isPriorityToManualAdd)
  }

  implicit val serializerConf: SerializerConf = createConf()
}

case class SerializerConf(
                           marshallerMetaInfAdd: Map[String, Marshaller],
                           marshallersManualAdd: Map[String, Marshaller],
                           isPriorityToManualAdd: Boolean = true
                         )
