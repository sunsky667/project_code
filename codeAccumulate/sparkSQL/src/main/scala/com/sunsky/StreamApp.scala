package com.sunsky

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object StreamApp {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(20))
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val lines = ssc.socketTextStream("192.168.80.128",9999)

    val stream = lines.map(line => line.split("\\|")).map(attributes => Row(attributes(0), attributes(1).trim , attributes(2) , attributes(3)))

    val schemaString = "name age sex create_time"
    val fields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType , true))
    val schema = StructType(fields)

    /**
      * 用于udf函数
      * @return
      */
    def getDate(): String = {
      val sdf = new SimpleDateFormat("yyyyMMdd")
      val date = new Date()
      sdf.format(date)
    }

    /**
      * 用于udf函数
      * @return
      */
    def getMin():String = {
      val sdf = new SimpleDateFormat("yyyyMMddHH")
      val date = new Date()
      val hourStr = sdf.format(date)
      val min = date.getMinutes / 5 * 5
      min match {
        case 0 => hourStr+"00"
        case 5 => hourStr+"05"
        case _ => hourStr+min.toString
      }
    }

    val sQLContext = spark.sqlContext
    sQLContext.udf.register("getDate",() => getDate())
    sQLContext.udf.register("getMin",() => getMin())

    stream.foreachRDD(
      rdd => {
        if(!rdd.isEmpty()){
          val pelpleDF = spark.createDataFrame(rdd,schema)
          pelpleDF.createOrReplaceTempView("people")
          val result = spark.sql("select * from people")
          spark.sql("select name,age,sex,create_time,getDate() as statis_day,getMin() as statis_min from people").show()
        }
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }
}
