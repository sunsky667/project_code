package com.sunsky.kafkaCycle

import kafka.serializer.StringDecoder
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka.KafkaUtils

/**
  * Created by sunsky on 2017/5/5.
  */
object CalStreaming {
  def createContext(ssc : StreamingContext): Unit = {
    val zkHost = "192.168.49.139:2181"
    val brokers = "192.168.49.139:9092"
    val topicStr = "test"
    val topicStr1 = "test1"
    val groupId = "motherfucker"

    val pvTopic = Set(topicStr)
    val pvTopic1 = Set(topicStr1)
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers,
      "serializer.class" -> "kafka.serializer.StringEncoder",
      "group.id" -> groupId,
      "num.consumer.fetchers" -> "10"
    )

    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, pvTopic)
    val lines = kafkaStream.map(_._2)
    val kafkaStream1 = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, pvTopic1)
    val lines1 = kafkaStream1.map(_._2)

    val rdd = lines.union(lines1)
    rdd.foreachRDD(
      rdd => rdd.foreach(
        println
      )
    )

  }
}
