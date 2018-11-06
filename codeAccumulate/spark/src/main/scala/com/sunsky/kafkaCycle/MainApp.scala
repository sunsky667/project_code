package com.sunsky.kafkaCycle

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by sunsky on 2017/5/5.
  */
object MainApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("mother fucker")
    val ssc = new StreamingContext(conf, Seconds(15))
    CalStreaming.createContext(ssc)
    ssc.start()
    ssc.awaitTermination()
  }
}
