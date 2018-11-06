package com.sunsky.changeBroadCast

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.log4j.{Level, Logger}

/**
  * Created by sunsky on 2017/7/11.
  */
object App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val ip = "192.168.80.128"
    val sparkConf = new SparkConf().setAppName("changeBroadCast").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(20))

    var instance: Broadcast[Set[String]] = null

    val set = Set("a","b")
    val seta = Set("d","e","f","g")

    val bcu = BroadcastUtil.apply(sc,set)
    instance = bcu.getInstance(sc,set)
    val lines = ssc.socketTextStream(ip ,9999)

    val rstRdd = lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_)

    lines.print()

    rstRdd.foreachRDD(
      rdd => {
        instance.value.foreach(record => println("==================before================> " + record))
        instance = bcu.update(rdd.sparkContext,seta)
        instance.value.foreach(record => println("==================after ================> " + record))
        rdd.foreach(
          record =>{
            println("============================record ============= "+record)
            instance.value.foreach(record => println("==================in each record ================> " + record))
          }
        )

        //测试在遍历rdd的时候调用sparkContext去读文件，看是不是每次都执行
        //结果判断是每次都执行
        val readrdd = rdd.sparkContext.textFile("D:\\tmp\\weibiao").map(line => (line.split("\\|",-1)(0),line.split("\\|",-1)(1)))

        readrdd.foreach(
          rd =>  println("#################"+rd._1+"@@@@@@@@@@@@@@@@@"+rd._2)
        )
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }

}
