package com.sunsky.updateStateByKeyApp

import org.apache.spark.streaming.{Duration, Minutes, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * update state by key!
 * to run this App , first input linux terminal ''nc -lk 9999''
 */
object App  {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[3]").setAppName("updateStateByKey")
    val sparkContext = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sparkContext,Seconds(5))
    UpdateStateByKeyDemo.caculate(ssc)
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
