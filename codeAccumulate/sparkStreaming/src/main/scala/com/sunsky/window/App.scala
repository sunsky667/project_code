package com.sunsky.window

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext, SparkFiles}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

/**
  * reduceByKeyAndWindow前面的时间是数据保留时间，后面的时间是数据计算时间
  * context的时间是rdd生成时间
  */
object App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("window").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(5))
    val lines = ssc.socketTextStream("192.168.80.128",9999)

    val mapedData = lines.flatMap(_.split(" ")).map((_,1))

    val result = mapedData.reduceByKeyAndWindow((v1:Int,v2:Int) => v1+v2,Seconds(60),Seconds(10))

    result.foreachRDD(
      rdd => {

        println("#############################################################"+System.currentTimeMillis())
        rdd.foreach(
          line => println("========="+line._1+"  ======== "+line._2)
        )
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }
}
