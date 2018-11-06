package com.sunsky.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  */
object AppJoin {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(20))
//    val spark = SparkSession.builder().config(conf).getOrCreate()

    val lines = ssc.socketTextStream("192.168.80.128",9999)

    lines.map(sp).filter(_ != null).foreachRDD(
      rdd => {
        val tmp = rdd.sparkContext.textFile("D:\\tmp\\weibiao").map(line => (line.split("\\|",-1)(0),line.split("\\|",-1)(1)))
        val rst = rdd.join(tmp)
        rst.foreach(
          record => println(System.nanoTime()+"key : "+record._2._1 + " value "+record._2._2)
        )
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }

  def sp(line:String):(String,String) = {
    if(line != null){
      val array = line.split("\\|",-1)
      if(array.length >= 2){
        (array(0),array(1))
      }else{
        null
      }
    }else{
      null
    }
  }
}
