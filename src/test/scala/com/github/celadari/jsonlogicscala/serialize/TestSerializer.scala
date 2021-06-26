package com.github.celadari.jsonlogicscala.serialize

import com.github.celadari.jsonlogicscala.TestPrivateMethods
import com.github.celadari.jsonlogicscala.exceptions.InvalidValueLogicException
import play.api.libs.json.{JsObject, JsValue, Json}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.{INT_CODENAME, STRING_CODENAME}
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, SimpleTypeValue}


class TestSerializer extends TestPrivateMethods {

  private[this] val serializeValueLogic = PrivateMethod[(JsValue, JsValue)](toSymbole("serializeValueLogic"))
  private[this] val serializeArrayOfConditions = PrivateMethod[(JsValue, JsObject)](toSymbole("serializeArrayOfConditions"))
  private[this] val serializeComposeLogic = PrivateMethod[(JsValue, JsObject)](toSymbole("serializeComposeLogic"))

  "Private method serializeValueLogic" should "return serialized ValueLogic" in {
    val valueLogic = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1"))
    val serializer = new Serializer
    val result = serializer invokePrivate serializeValueLogic(valueLogic)
    result shouldBe (Json.parse("""{"var":"data1","type":{"codename":"int"}}"""), Json.parse("""{"data1":45}"""))
  }

  "Private method serializeValueLogic value but no typeCodenameOpt" should "throw an exception" in {
    val valueLogic = ValueLogic(Some(45), None, pathNameOpt = Some("data1"))
    val serializer = new Serializer
    val thrown = the[InvalidValueLogicException] thrownBy {serializer invokePrivate serializeValueLogic(valueLogic)}
    thrown.getMessage shouldBe "ValueLogic with defined value must define a typeCodename as well"
  }

  "Private method serializeValueLogic empty value" should "return serialized ValueLogic" in {
    val valueLogic = ValueLogic(None, None, pathNameOpt = Some("dataNull"))
    val serializer = new Serializer
    val result = serializer invokePrivate serializeValueLogic(valueLogic)
    result shouldBe (Json.parse("""{"var":"dataNull","type":"null"}"""), Json.parse("""{"dataNull":null}"""))
  }

  "Private method serializeArrayOfConditions" should "return serialized conditions" in {
    val valueLogic1 = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1"))
    val valueLogic2 = ValueLogic(Some("I love New York"), Some(SimpleTypeValue(STRING_CODENAME)), pathNameOpt = Some("data2"))
    val valueLogic3 = ValueLogic(None, None, pathNameOpt = Some("dataNull"))
    val valueLogic4 = ValueLogic(None, None, variableNameOpt = Some("acc"), None)
    val composeLogic1 = new ComposeLogic("+", Array(
      ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
      ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2"))
    ))

    val serializer = new Serializer
    val result = serializer invokePrivate serializeArrayOfConditions(Array(valueLogic1, valueLogic2, valueLogic3, valueLogic4, composeLogic1))
    result shouldBe (Json.parse("""[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"string"}},{"var":"dataNull","type":"null"},{"var":"acc"},{"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]}]"""),
      Json.parse("""{"data1":45,"data2":65,"dataNull":null}"""))
  }

  "Private method serializeComposeLogic" should "return serialized conditions" in {
    val composeLogic = new ComposeLogic("map", Array(
      ValueLogic(Some(Array(43, 78, 2, 0)), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("dataArr")),
      new ComposeLogic("+", Array(
        ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
        ValueLogic(None, None, variableNameOpt = Some(""), None)
      ))
    ))

    val serializer = new Serializer
    val result = serializer invokePrivate serializeComposeLogic(composeLogic)
    result shouldBe (Json.parse("""{"map":[{"var":"dataArr","type":{"codename":"array","paramType":{"codename":"int"}}},{"+":[{"var":"data1","type":{"codename":"int"}},{"var":""}]}]}"""),
      Json.parse("""{"dataArr":[43,78,2,0],"data1":45}"""))
  }

  "Serialize method VariableLogic" should "throw an exception" in {
    val valueLogic = VariableLogic("acc", new ComposeLogic("+", Array()))
    val serializer = new Serializer
    val thrown = the[InvalidValueLogicException] thrownBy {serializer.serialize(valueLogic)}
    thrown.getMessage shouldBe "VariableLogic cannot be serialized. Resulting tree from CompositionOperator.ComposeJsonLogicCore is only used at evaluation."
  }

  "Serialize default ComposeLogic" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
      ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2"))
    )).asInstanceOf[JsonLogicCore]

    Json.stringify(Json.toJson(tree)) shouldBe """[{"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]},{"data1":45,"data2":65}]"""
  }

}
