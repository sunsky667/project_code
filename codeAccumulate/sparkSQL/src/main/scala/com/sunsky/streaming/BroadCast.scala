package com.sunsky.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object BroadCast {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(20))

    var bcparam : Broadcast[mutable.Map[String,String]] = null
    val tmp = sc.textFile("D:\\tmp\\weibiao").map(line => (line.split("\\|",-1)(0),line.split("\\|",-1)(1)))
    val map : mutable.Map[String,String] = mutable.Map[String,String]()
    tmp.collect().foreach(
      line => map += (line._1 -> line._2)
    )
    bcparam = sc.broadcast(map)

    val lines = ssc.socketTextStream("192.168.80.128",9999)

    lines.map(sp).filter(_ != null).map(line => (line._2,bcparam.value.getOrElse(line._1,null)))
      .filter(line => line._2 != null).foreachRDD(
      rdd => {
        rdd.foreachPartition(
          p => p.foreach(
            r => println("==========r=============="+r)
          )
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
