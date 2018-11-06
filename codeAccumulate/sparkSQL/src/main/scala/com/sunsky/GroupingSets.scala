package com.sunsky

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object GroupingSets {

  def main(args: Array[String]): Unit = {

    val con = new SparkConf().setAppName("sql").setMaster("local[3]")
    val spark = SparkSession
      .builder()
      .config(con)
      .getOrCreate()

    groupingSets(spark)

  }

  def groupingSets(spark:SparkSession) = {
    val df = spark.read.json("d://file//user.json")
    df.createTempView("people")
    val result = spark.sql("select sum(age) as age ,sex,create_time " +
      "from people group by sex,create_time " +
      "grouping sets((sex,create_time),(sex))")

    result.createOrReplaceTempView("result")

    spark.sql("select age,sex,nvl(create_time,'-999') as time from result").show()
  }

}
