package com.sunsky.changeBroadCast.readRedis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable


object App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf()
      .setAppName("changeBroadCast")
      .setMaster("local[3]")
      .set("spark.streaming.stopGracefullyOnShutdown","true")

    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(20))

    var instance: Broadcast[mutable.Map[String,String]] = null

    val jedis = RedisClient.getClient()
    val result = jedis.hgetAll("k")
    jedis.close()
    import scala.collection.JavaConverters._
    instance = sc.broadcast(result.asScala)

    val lines = ssc.socketTextStream("192.168.80.128",9999)

    lines.foreachRDD(
      rdd => {
        println("--------------------------------------------------------------------------")
        instance.value.foreach(
          line => println(System.nanoTime()+" bordcast == key "+line._1+" value : "+line._2)
        )

        update(rdd.sparkContext,true)

        rdd.foreach(
          r => println("@@@@@ "+r)
        )
      }
    )



    def update(sc: SparkContext, blocking: Boolean = false): Unit ={
      if(instance != null){
        instance.unpersist(blocking)
        val jedis = RedisClient.getClient()
        val result = jedis.hgetAll("k").asScala
        instance = sc.broadcast(result)
        jedis.close()
      }
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      override def run() {
        ssc.stop(true, true)
      }
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
