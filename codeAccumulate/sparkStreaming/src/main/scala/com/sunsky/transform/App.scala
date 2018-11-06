package com.sunsky.transform

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("transform").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(10))
    val lines = ssc.socketTextStream("192.168.80.128",9999)

    val r = lines.transform(
      rdd => {
        val id = rdd.id
        val result = rdd.map(line => (line+id,id)).filter(_._1 != null)
        result
      }
    )


    r.foreachRDD(
      rdd => {
        println("rdd name is : "+rdd.name)
        rdd.foreach(
        record => println(record)
      )}
    )

    ssc.start()
    ssc.awaitTermination()
  }
}
