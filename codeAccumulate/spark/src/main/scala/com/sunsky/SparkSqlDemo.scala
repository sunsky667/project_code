package com.sunsky

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/5/4.
  */
object SparkSqlDemo {

  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("sb")
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    val df = spark.read.json("D://file//user.json")

    //df.printSchema()
    //df.select("name").show()
//    df.groupBy("age").count().show()
//    import spark.implicits._
    //    df.filter($"age" > 21).show()
    //    df.select($"name", $"age" + 1).show()
    df.createOrReplaceTempView("people")
    val sqlDF = spark.sql("SELECT * FROM people")
    sqlDF.show()
  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }
}
