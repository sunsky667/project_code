package com.sunsky.optRdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/7/5.
  */
object MainApp {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("optRdd").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(20))
    val lines = ssc.socketTextStream("192.168.80.128",9999)


    val array = Array("1","2")

    val bc = sc.broadcast(array)

    bc.value.foreach(
      r => {
        lines.map(line => (line.split("\\|")(0),line)).filter(s => s._1 == r)
          .foreachRDD(
            rdd => rdd.foreach(
              record => println("<=================> "+r+" <================> "+record)
            )
          )
      }
    )

    //lines.print()


    ssc.start()
    ssc.awaitTermination()
  }
}
