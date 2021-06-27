package com.github.celadari.jsonlogicscala

import scala.util.control.Breaks
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
/*
trait JsonLogicCoreMatcher extends AnyFlatSpec with Matchers {

  def findFirstNonMatchingLine(rightValues: Array[Seq[Any]], leftValues: Array[Seq[Any]]): Option[String] = {
    var firstLine: Option[String] = None
    val zipped = leftValues.zip(rightValues).zipWithIndex
    val loop = new Breaks
    loop.breakable {
      for (((seq1, seq2), idx) <- zipped) {
        if (seq1 != seq2) {
          val str1 = seq1.mkString("[", ", ", "]")
          val str2 = seq2.mkString("[", ", ", "]")
          firstLine = Some(s"DataFrames are not equals. Line $idx:\nActual   $str1\nExpected $str2\n")
          loop.break
        }
      }
    }
    firstLine
  }

  class BeEqualJsonLogicCore(right: JsonLogicCore) extends Matcher[JsonLogicCore] {

    def compare(left: ValueLogic[_], right: JsonLogicCore): Boolean = {
      right match {
        case _: ComposeLogic => false
        case _: VariableLogic => false
        case valueLogic: ValueLogic[_]
      }
    }

    override def apply(left: JsonLogicCore): MatchResult = {
      left match {
        case valueLogic: ValueLogic[_] =>
      }

      // first compare schemas
      val leftSchema = left.schema.map(field => (field.name, field.dataType))
      val rightSchema = right.schema.map(field => (field.name, field.dataType))
      if (leftSchema != rightSchema) {
        MatchResult(matches = false, s"DataFrames are not equals. Schemas are not the same:" +
          s"$leftSchema != $rightSchema", "DataFrames are equals.")
      } else {
        // sort rows into right order
        val cols = left.columns.map(colName => s"`$colName`")
        val numCols = (1 to cols.length).map(_.toString).toArray
        // change header to allow .na.fill
        val replaceNaNAndNull =  right.toDF(numCols:_*).schema.fields
          .filter(field => field.dataType != NullType && field.dataType != StringType)
          .map(_.name)
          .map((_, "null"))
          .toMap
        var rightOrderedDF = right.toDF(numCols:_*).na.fill(replaceNaNAndNull).toDF(left.columns:_*)
        var leftOrderedDF = left.toDF(numCols:_*).na.fill(replaceNaNAndNull).toDF(left.columns:_*)
        if (sort) rightOrderedDF = rightOrderedDF.sort(cols.map(col): _*)
        if (sort) leftOrderedDF = leftOrderedDF.sort(cols.map(col): _*)
        val rightValues = (if (nbLinesOpt.isDefined) rightOrderedDF.take(nbLinesOpt.get) else rightOrderedDF.collect).map(_.toSeq)
        val leftValues = (if (nbLinesOpt.isDefined) leftOrderedDF.take(nbLinesOpt.get) else leftOrderedDF.collect).map(_.toSeq)
        val diff = findFirstNonMatchingLine(rightValues, leftValues)
        if (diff.isEmpty)
          MatchResult(leftValues.length == rightValues.length, s"Result DataFrame and Expected DataFrame have not" +
            s"same count: ${leftValues.length} != ${rightValues.length}", "DataFrames are equals.")
        else
          MatchResult(
            diff.isEmpty,
            diff.getOrElse("DataFrames are not equals"),
            "DataFrames are equals.")
      }
    }
  }

  def BeEqualToDF(right: DataFrame): BeEqualDF = new BeEqualDF(right, None, true)
  def BeEqualToDF(nbLines: Int)(right: DataFrame): BeEqualDF = new BeEqualDF(right, Some(nbLines), true)
  def BeEqualToDF(sort: Boolean)(right: DataFrame): BeEqualDF = new BeEqualDF(right, None, sort)
  def BeEqualToDF(nbLines: Int, sort: Boolean)(right: DataFrame): BeEqualDF = new BeEqualDF(right, Some(nbLines), sort)

}

object JsonLogicCoreMatcher extends JsonLogicCoreMatcher
*/