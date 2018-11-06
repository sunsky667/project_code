package com.sunsky.structedstreaming

import com.sunsky.entity.Person
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    quickstart(spark)
//    first(spark)
//    basicOperations(spark)
  }

  /**
    * quick start
    * @param spark
    */
  def quickstart(spark : SparkSession) = {
    import spark.implicits._

    val lines = spark.readStream.format("socket").option("host","192.168.80.128").option("port",9999).load()

//    lines.printSchema()
//
//    lines.createOrReplaceTempView("fuck")
//
//    spark.sql("select count(value) from fuck").writeStream.outputMode("complete").format("console").start()

    val words = lines.as[String].flatMap(_.split(" "))

    val wordsCount = words.groupBy("value").count()

    val query = wordsCount.writeStream.outputMode("complete").format("console").start()

    query.awaitTermination()
  }

  def first(spark : SparkSession) = {
    import spark.implicits._

    val lines = spark.readStream.format("socket").option("host","192.168.80.128").option("port",9999).load()

    println("=================="+lines.isStreaming)

    lines.createOrReplaceTempView("tmp")
    lines.printSchema()

    val user = spark.sql("select split(value,' ')[0] as name,split(value,' ')[1] as value from tmp")
    user.createOrReplaceTempView("user")

    spark.sql("select count(distinct(name)) from user").writeStream.outputMode("complete").format("console").start().awaitTermination()

  }

  //Basic Operations - Selection, Projection, Aggregation
  def basicOperations(spark:SparkSession) = {
    import spark.implicits._
    val lines = spark.readStream.format("socket").option("host","192.168.80.128").option("port",9999).load()

    val ds = lines.as[String]

    val personDF = ds.map(line => line.split("\\|")).map(row => Person(row(0),row(1).trim.toInt,row(2),row(3)))//.as[Person]

    personDF.printSchema()

    val query = personDF.select("age").where("age > 25").groupBy("age").count() //use untyped APIs --- for dataframe

    import org.apache.spark.sql.expressions.scalalang.typed
    val queryDS = personDF.as[Person].groupByKey(_.sex).agg(typed.avg(_.age.toDouble))  //use typed APIs

    query.writeStream.format("console").outputMode("complete").start().awaitTermination()
    queryDS.writeStream.format("console").outputMode("complete").start().awaitTermination()
  }
}
