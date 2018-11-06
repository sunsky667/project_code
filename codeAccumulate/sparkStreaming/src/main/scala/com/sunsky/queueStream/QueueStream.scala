package com.sunsky.queueStream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * warning
  * 经验证，只读一次
  */
object QueueStream {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("TestRDDQueue").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(20))
    val rddQueue =new scala.collection.mutable.SynchronizedQueue[RDD[Int]]()
    val queueStream = ssc.queueStream(rddQueue)
    val mappedStream = queueStream.map(r => (r % 10, 1))
    val reducedStream = mappedStream.reduceByKey(_ + _)
    //reducedStream.print()

    reducedStream.foreachRDD(
      rdd => {
        println("=============")
        rdd.foreach(
          record => println("#############"+record)
        )
      }
    )

    ssc.start()
    for (i <- 1 to 10){
      println("==============================")
      rddQueue += ssc.sparkContext.makeRDD(1 to 100,2)
      Thread.sleep(1000)
    }
    ssc.awaitTermination()
    //ssc.stop()
  }
}
