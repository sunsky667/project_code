package com.sunsky.muti

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

import scala.collection.mutable.ArrayBuffer

object Muti {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("muti").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(10))
    val lines = ssc.socketTextStream("192.168.80.128",9999)

    def getarray(line:String):Array[(String,Array[Int])] = {
      val wordsArrayBuffer = ArrayBuffer[(String,Array[Int])]()
      val words = line.split(" ")
      words.foreach(
        word => {
          //val value = new Array[Int](1)
          val value = new Array[Int](1)
          value(0) = 1
          val rest = (word,value)
          wordsArrayBuffer += rest
        }
      )
      wordsArrayBuffer.toArray
    }

    lines.map(getarray).flatMap(r => r).reduceByKey(
      (pre, after) => Array(pre(0) + after(0))
    ).foreachRDD(
      rdd => {
        rdd.foreachPartition(
          p => {
            p.foreach(
              record => {
                println(record._1 +" ===================> "+record._2(0))
              }
            )
          }
        )
      }
    )
    ssc.start()
    ssc.awaitTermination()
  }
}
