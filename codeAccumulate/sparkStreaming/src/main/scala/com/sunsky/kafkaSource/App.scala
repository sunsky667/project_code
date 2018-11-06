package com.sunsky.kafkaSource

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * Created by sunsky on 2017/3/29.
  */
object App {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test_spark").setMaster("local[3]")
    val ssc = new StreamingContext(conf,Seconds(20))
    //val brokers = "10.200.63.11:9092,10.200.63.12:9092,10.200.63.13:9092,10.200.63.14:9092,10.200.63.15:9092,10.200.63.16:9092,10.200.63.17:9092,10.200.63.18:9092,10.200.63.19:9092,10.200.63.20:9092"
    val brokers = "192.168.80.128:9092"
    //val topic = "user-topic"
    val topic = "test"
    val groupid = "test-kafka"
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers
    )
    val sTopics = Set(topic)

    val kafkaStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc, kafkaParams, sTopics)

    val lines = kafkaStream.map(_._2)
    lines.print(15)

    

//   lines.map(items => println("======================================================================>" + items))
//   lines.foreachRDD(rdd => rdd.foreach(items => println(items)))
    lines.foreachRDD(rdd => rdd.foreachPartition(items => items.foreach(str => println("===============================================================> "+str))))
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }

}
