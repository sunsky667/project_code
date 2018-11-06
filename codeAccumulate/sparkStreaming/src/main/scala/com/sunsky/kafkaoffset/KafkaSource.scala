package com.sunsky.kafkaoffset

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZKStringSerializer, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}

/**
  * created by liulili
  * 2018 Apr 10
 *
  * @param zkHost
  * @param brokers
  * @param groupId
  * @param topic
  *
  * if method createDirectStream parameter kafkaParams
  * have not set "auto.offset.reset" -> "smallest",
  * also zookeeper not save the offset of this topic group,
  * the kafka get stream from largest offset
  *
  * 如果createDirectStream方法没有设置
  * "auto.offset.reset" -> "smallest"
  * zk里面也没有保存该topic的消费组的offset
  * 那么kafka会从最新的消费地址去消费
  *
  */
case class KafkaSource(zkHost:String,brokers:String, groupId:String, topic:String){

  private val zkClient = new ZkClient(zkHost, 60000, 30000, ZKStringSerializer)
  private val zkClientUTF8 = new ZkClient(zkHost)
  private val topicDirs = new ZKGroupTopicDirs(groupId, topic)

  private val existsChildren:Boolean = KafkaOffset.hasChildrenPath(zkClient,topicDirs)



  /**
    * create DStream , get data from kafka
    * and save the offset to zookeeper
    * @param ssc
    * @param kafkaParams
    * @return
    */
  def createDirectStream(ssc:StreamingContext,kafkaParams:Map[String, String]) : DStream[String] = {

    var kafkaStream:InputDStream[(String, String)]= null

    if(existsChildren){
      //Define a function . Attention between Function and Method in scala language
      val messageHandler = (messageAndMetadata : MessageAndMetadata[String, String]) => (messageAndMetadata.topic, messageAndMetadata.message())
//      val messageHandler = Handler.msgHandler(_)  //can also use this to define a function
      val fromOffsets:Map[TopicAndPartition, Long] = KafkaOffset.getOffSet(zkClient,zkClientUTF8,brokers,groupId,topic,topicDirs)
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, messageHandler)
    }else{
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set(topic))
    }

    var offsetRanges = Array[OffsetRange]()

    val lines: DStream[String] = kafkaStream.transform { rdd =>

      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges //得到该 rdd 对应 kafka 的消息的 offset

      //存入zk
      for (o <- offsetRanges) {

        val zkPath = topicDirs.consumerOffsetDir+"/"+o.partition

        ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString) //将该 partition 的 offset 保存到 zookeeper
      }
      rdd
    }.map(_._2)

    lines
  }

}

