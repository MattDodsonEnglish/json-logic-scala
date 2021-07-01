package com.github.celadari.jsonlogicscala.deserialize

import java.util.Properties
import scala.jdk.CollectionConverters.MapHasAsScala
import scala.reflect.runtime.{universe => ru}
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.{MissingAccessorException, ObjectRecipe}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.deserialize.defaults._
import com.github.celadari.jsonlogicscala.exceptions.ConfigurationException


object DeserializerConf {
  val DEFAULT_UNMARSHALLERS: Map[String, Unmarshaller] = Map(
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

  def getOrCreateUnmarshaller(fileName: String, prop: Properties): (String, Unmarshaller) = {
    if (!prop.containsKey("className")) throw new ConfigurationException(s"Property file '$fileName' must define key 'className'")
    if (!prop.containsKey("codename")) throw new ConfigurationException(s"Property file '$fileName' must define key 'codename'")
    val className = prop.remove("className").toString
    val typeCodename = prop.remove("codename").toString

    val isObject = if (prop.containsKey("singleton")) prop.remove("singleton").toString.toBoolean else false
    if (isObject) {
      val m = ru.runtimeMirror(getClass.getClassLoader)
      try {
        (typeCodename, m.reflectModule(m.staticModule(className)).instance.asInstanceOf[Unmarshaller])
      }
      catch {
        case castException: java.lang.ClassCastException => throw new ConfigurationException(s"Found object is not a Unmarshaller instance: \n${castException.getMessage}")
        case _: Throwable => throw new ConfigurationException(s"No singleton object found for: $className\nCheck if 'className' '$className' is correct and if 'singleton' property in '$fileName' property file is correct")
      }
    }
    else {
      try {
        val objectRecipe = new ObjectRecipe(className)
        val sep = if (prop.containsKey("sep")) prop.remove("sep").toString else ";"
        if (prop.containsKey("constructorArgNames")) objectRecipe.setConstructorArgNames(prop.remove("constructorArgNames").toString.split(sep))
        objectRecipe.setAllProperties(prop)
        (typeCodename, objectRecipe.create().asInstanceOf[Unmarshaller])
      }
      catch {
        case castException: java.lang.ClassCastException => throw new ConfigurationException(s"Found class is not a Unmarshaller instance: \n${castException.getMessage}")
        case _: MissingAccessorException => throw new ConfigurationException(s"Field error, check that no field in '$className' is missing in '$fileName' property file.\nCheck that no property in '$fileName' file is not undefined in '$className' class.\nCheck if '$className' class constructor requires arguments or if argument names defined in '$fileName' property file are correct")
        case _: Throwable => throw new ConfigurationException(s"No class found for: $className\nCheck if 'className' '$className' is correct and if 'singleton' property in '$fileName' property file is correct")
      }
    }
  }

  def createConf(
                  path: String = "META-INF/services/",
                  unmarshallersManualAdd: Map[String, Unmarshaller] = DEFAULT_UNMARSHALLERS,
                  isPriorityToManualAdd: Boolean = true
                ): DeserializerConf = {
    val finder = new ResourceFinder(path)
    val props = finder.mapAllProperties(classOf[Unmarshaller].getName).asScala
    val unmarshallersMetaInf = props.map{case (fileName, prop) => getOrCreateUnmarshaller(fileName, prop)}.toMap
    DeserializerConf(unmarshallersMetaInf, unmarshallersManualAdd, isPriorityToManualAdd)
  }

  implicit val deserializerConf: DeserializerConf = createConf()
}

case class DeserializerConf(
                             unmarshallerMetaInfAdd: Map[String, Unmarshaller],
                             unmarshallersManualAdd: Map[String, Unmarshaller],
                             isPriorityToManualAdd: Boolean = true
                           )
