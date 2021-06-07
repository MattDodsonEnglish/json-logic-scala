package com.github.celadari.jsonlogicscala.deserialize.defaults2

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros


@compileTimeOnly("enable macro paradise to expand macro annotaions")
class default_unmarshaller extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro DefaultUnmarshallers.impl
}