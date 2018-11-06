package com.sunsky.pkgtrait

import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.dstream.DStream

trait Calculate {

  def getStreamingContext(): StreamingContext = {
    val sparkConf = new SparkConf().setAppName("savehbase").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(5))
    ssc
  }

  def input(ssc : StreamingContext):DStream[String] = {
    val lines = ssc.socketTextStream("192.168.80.128",9999)
    lines
  }

  def start(ssc : StreamingContext): Unit = {
    ssc.start()
    ssc.awaitTermination()
  }

}
