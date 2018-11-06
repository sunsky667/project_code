package com.sunsky.kafkaoffset

import kafka.api.{OffsetRequest, PartitionOffsetRequestInfo}
import kafka.client.ClientUtils
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import kafka.serializer.StringDecoder
import kafka.utils.ZKGroupTopicDirs
import org.I0Itec.zkclient.ZkClient
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.dstream.DStream
import org.apache.zookeeper.ZooKeeper

import scala.collection.mutable.ListBuffer

object KafkaApp {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test_spark").setMaster("local[3]")
    val ssc = new StreamingContext(conf,Seconds(20))


    val brokers = "192.168.80.128:9092"
    val topic = "test"
    val groupId = "test-kafka1"
    val zk = "192.168.80.128:2181"
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers,
      "group.id" -> groupId,
      "zookeeper.connect" -> zk,
      "num.consumer.fetchers" -> "3"
    )
    val kafkaSource:KafkaSource = KafkaSource(zk,brokers,groupId,topic)
    val lines = kafkaSource.createDirectStream(ssc,kafkaParams)



//    val sTopics = Set(topic)
//    val kafkaStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc, kafkaParams, sTopics)
//
//    var offsetRanges = Array[OffsetRange]()
//    val lines: DStream[String] = kafkaStream.transform { rdd =>
//      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges //得到该 rdd 对应 kafka 的消息的 offset
//      rdd
//    }.map(_._2)
//
//    lines.foreachRDD(rdd => rdd.foreachPartition(items => items.foreach(str => println("===============================================================> "+str))))
//
//    lines.transform( r => {
//      val one = r.take(1)
//      ssc.sparkContext.makeRDD(one)
//    }).foreachRDD(
//      rdd => {
//        for (o <- offsetRanges) {
//          println("#############################>"+o)
//        }
//      }
//    )

    lines.foreachRDD(rdd => rdd.foreachPartition(items => items.foreach(str => println("===============================================================> "+str))))

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }


}
