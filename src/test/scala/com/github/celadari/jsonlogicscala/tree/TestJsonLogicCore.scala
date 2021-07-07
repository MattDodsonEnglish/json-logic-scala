package com.github.celadari.jsonlogicscala.tree

import com.github.celadari.jsonlogicscala.evaluate.defaults.TestNumeric
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TestJsonLogicCore extends AnyFlatSpec with Matchers with TestNumeric {

  "Method treeString" should "return string tree representation" in {
    val composeLogic = new ComposeLogic("+", Array(
      new ComposeLogic("*", Array(
        ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data1")),
        ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data2"))
      )),
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data3"))
    ))
    val expectedString = """+
                           |├──*
                           |│  ├──{ValueLogic Data 'data1': 5}
                           |│  └──{ValueLogic Data 'data2': 47}
                           |└──{ValueLogic Data 'data3': 5}""".stripMargin

    composeLogic.treeString shouldBe expectedString
  }

}
