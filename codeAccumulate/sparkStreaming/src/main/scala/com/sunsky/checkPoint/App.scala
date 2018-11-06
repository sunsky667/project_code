package com.sunsky.checkPoint

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Minutes, StreamingContext}

object App {

  def main(args: Array[String]): Unit = {


    val ssc = StreamingContext.getOrCreate("check point path",createContext)

    ssc.start()
    ssc.awaitTermination()

  }


  /**
    * 这里有有两个坑：
    (1)处理的逻辑必须写在functionToCreateContext函数中，你要是直接写在main方法中，
  在首次启动后，kill关闭，再启动就会报错
    (2)更改代码后之前的checkpoint就不能用
    * @return
    */
  def createContext():StreamingContext = {
    val sparkConf = new SparkConf()
      .setAppName("checkPoint")
    val sc = new SparkContext(sparkConf)
    val streamContext = new StreamingContext(sc,Minutes(5))

    //get data source

    streamContext.checkpoint("check point path")

    //do biz

    streamContext
  }
}
