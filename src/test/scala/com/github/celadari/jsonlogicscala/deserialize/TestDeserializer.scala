package com.github.celadari.jsonlogicscala.deserialize

import com.github.celadari.jsonlogicscala.TestPrivateMethods
import com.github.celadari.jsonlogicscala.exceptions.{InvalidJsonParsingException, InvalidValueLogicException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.{DOUBLE_CODENAME, FLOAT_CODENAME, INT_CODENAME, LONG_CODENAME, NULL_CODENAME, STRING_CODENAME}
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import play.api.libs.json.{JsObject, JsValue, Json}


class TestDeserializer extends TestPrivateMethods {

  private[this] val deserializeValueLogic = PrivateMethod[ValueLogic[_]](toSymbol("deserializeValueLogic"))
  private[this] val deserializeArrayOfConditions = PrivateMethod[Array[JsonLogicCore]](toSymbol("deserializeArrayOfConditions"))
  private[this] val deserializeComposeLogic = PrivateMethod[ComposeLogic](toSymbol("deserializeComposeLogic"))

  "Private method deserializeValueLogic" should "return deserialized ValueLogic" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data1","type":{"codename":"int"}}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val resultExpected = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1"))
    val result = deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)
    result shouldBe resultExpected
  }

  "Private method deserializeValueLogic type but undefined path data" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data2","type":{"codename":"int"}}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "Error while parsing ValueLogic of type value: \"var\" path is undefined"
  }

  "Private method deserializeValueLogic defined path data but no type" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data1"}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "Error while parsing ValueLogic of type variable: \"var\" must not be a key on data dictionary"
  }

  "Private method deserializeValueLogic type null" should "return deserialized ValueLogic" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data1","type":{"codename":"null"}}"""), Json.parse("""{"data1":null}"""))
    val deserializer = new Deserializer
    val resultExpected = ValueLogic(None, Some(SimpleTypeValue(NULL_CODENAME)), pathNameOpt = Some("data1"))
    val result = deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)
    result shouldBe resultExpected
  }

  "Private method deserializeValueLogic wrong type json format" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data2","type":"int"}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "Invalid typevalue json format: \"int\""
  }

  "Private method deserializeComposeLogic composeLogic json more than one field" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"*":[{"var":"data1","type":{"codename":"int"}}, {"var":"data1","type":{"codename":"int"}}], "+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]}"""), Json.parse("""{"data1":null}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeComposeLogic(jsLogic, jsData)}
    val expectedMessage = """ComposeLogic cannot have more than one operator field.
                            |Current operators: [*, +]
                            |Invalid ComposeLogic json: {"*":[{"var":"data1","type":{"codename":"int"}},{"var":"data1","type":{"codename":"int"}}],"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]}""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "Private method deserializeComposeLogic composeLogic empty" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("{}"), Json.parse("""{"data1":null}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeComposeLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "ComposeLogic cannot be empty"
  }

  "Private method deserializeArrayOfConditions" should "return deserialized conditions" in {
    val (jsLogic, jsData) = (Json.parse("""[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]"""), Json.parse("""{"data1":45,"data2":65}"""))
    val deserializer = new Deserializer
    val resultExpected = Array(
      ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
      ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2"))
    )
    val result = deserializer invokePrivate deserializeArrayOfConditions(jsLogic, jsData)
    result shouldBe resultExpected
  }

  "Deserialize ComposeLogic map operator" should "return value" in {
    val (jsLogic, jsData) = (Json.parse("""{"map":[{"var":"data1","type":{"codename":"array","paramType":{"codename":"int"}}},{"+":[{"+":[{"var":"data3","type":{"codename":"long"}},{"var":"data5","type":{"codename":"double"}},{"var":"data4","type":{"codename":"float"}}]},{"var":"data2","type":{"codename":"int"}},{"var":""}]}]}"""), Json.parse("""{"data1":[4,5,6,9],"data3":54,"data5":53,"data4":74,"data2":65}"""))
    val deserializer = new Deserializer
    val resultExpected = new ComposeLogic("map", Array(
      ValueLogic(Some(Array(4, 5, 6, 9)), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1")),
      new ComposeLogic("+", Array(
        new ComposeLogic("+", Array(
          ValueLogic(Some(54L), Some(SimpleTypeValue(LONG_CODENAME)), pathNameOpt = Some("data3")),
          ValueLogic(Some(53d), Some(SimpleTypeValue(DOUBLE_CODENAME)), pathNameOpt = Some("data5")),
          ValueLogic(Some(74f), Some(SimpleTypeValue(FLOAT_CODENAME)), pathNameOpt = Some("data4"))
        )),
        ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2")),
        ValueLogic(None, None, variableNameOpt = Some(""), None)
      ))
    )).asInstanceOf[JsonLogicCore]

    val result = deserializer.deserialize(jsLogic.asInstanceOf[JsObject], jsData.asInstanceOf[JsObject])
    println(result.operator)
    println(result.asInstanceOf[ComposeLogic].conditions.toSeq)
    println(resultExpected.operator)
    println(resultExpected.asInstanceOf[ComposeLogic].conditions.toSeq)
    result shouldBe resultExpected
  }

}
