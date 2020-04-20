package com.spark.assignment1

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.apache.spark.sql.functions._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Column, DataFrame, Row}

object Assignment1 {

  private val timestampFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm")

  /**
    * Helper function to print out the contents of an RDD
    * @param label Label for easy searching in logs
    * @param theRdd The RDD to be printed
    * @param limit Number of elements to print
    */
  private def printRdd[_](label: String, theRdd: RDD[_], limit: Integer = 20) = {
    val limitedSizeRdd = theRdd.take(limit)
    println(s"""$label ${limitedSizeRdd.toList.mkString(",")}""")
  }

/*using the sortBy method and .tak*/
  def problem1(tripData: RDD[Trip]): Long = {
   tripData.sortBy(_.duration, ascending = false).take(1)(0).duration
  }

  def problem2(trips: RDD[Trip]): Long = {
    trips.filter(_.start_station == "San Antonio Shopping Center").count()
  }

  def problem3(trips: RDD[Trip]): Seq[String] = {
    trips.map(_.subscriber_type).distinct().collect()
  }

  def problem4(trips: RDD[Trip]): String = {
    val counts = trips.map(x => (x.zip_code, x)).countByKey()
    val maxValue = counts.valuesIterator.max
    val maxKey = counts.filter { case (_, v) => v == maxValue }
    maxKey.keys.head
  }

  def problem5(trips: RDD[Trip]): Long = {
    trips.filter(x=> x.start_date.substring(0,9)!=x.end_date.substring(0,9)).count()
  }

  def problem6(trips: RDD[Trip]): Long = {
    trips.count()
  }

  def problem7(trips: RDD[Trip]): Double = {
    trips.filter(x=> x.start_date.substring(0,9)!=x.end_date.substring(0,9)).count().toDouble/trips.count()
  }

  def problem8(trips: RDD[Trip]): Double = {
    def a = (accu:Long, v:Trip) => accu + v.duration
    def b = (accu1:Long,accu2:Long) => accu1 + accu2
    trips.aggregate(0l)(a,b).toDouble * 2
  }

  def problem9(trips: RDD[Trip], stations: RDD[Station]): (Double, Double) = {
    val stationId = trips.filter(_.trip_id=="913401").map(_.start_station).collect().head
    println(stationId)
    val coordinates = stations.filter(_.name==stationId).map(x=> (x.lat,x.lon)).collect().head
    println(coordinates)
    coordinates
  }

  def problem10(trips: RDD[Trip], stations: RDD[Station]): Array[(String, Long)] = {
    val durations = trips.map(x=> (x.start_station,x.duration)).reduceByKey (_+_)
    val name_stations = stations.map(x=>(x.name,x))
    durations.join(name_stations).map(x=>(x._1,x._2._1)).collect()
  }

  def dfProblem11(trips: DataFrame): DataFrame = {
    trips.select(trips("trip_id"))
  }

  def dfProblem12(trips: DataFrame): DataFrame = {
    trips.filter(trips("start_station") === "Harry Bridges Plaza (Ferry Building)")
  }

  def dfProblem13(trips: DataFrame): Long = {
    trips
      .select(sum("duration")).collect().head.getLong(0)
  }

  // Helper function to parse the timestamp format used in the trip dataset.
  private def parseTimestamp(timestamp: String) = LocalDateTime.from(timestampFormat.parse(timestamp))
}
