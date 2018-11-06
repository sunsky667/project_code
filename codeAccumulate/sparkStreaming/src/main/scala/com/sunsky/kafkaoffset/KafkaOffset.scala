package com.sunsky.kafkaoffset

import kafka.api.{OffsetRequest, PartitionOffsetRequestInfo}
import kafka.client.ClientUtils
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import kafka.utils.ZKGroupTopicDirs
import org.I0Itec.zkclient.ZkClient

/**
  * created by liulili
  * 2018 Apr 10
  * this object dependency org.I0Itec.zkclient.ZkClient
  * the pom files need include org.I0Itec.zkclient.ZkClient dependency
  */
object KafkaOffset {

  /**
    * function : get the topic all partitions from kafka Brokers
    * @param brokers
    * @param topic
    * @return partitions
    */
  def getPartitions(brokers:String,topic:String) : Seq[Int] = {
    val topicSet = Set(topic)
    val clientId = "GetOffsetShell"
    val metadataTargetBrokers = ClientUtils.parseBrokerList(brokers)
    val topicsMetadata = ClientUtils.fetchTopicMetadata(topicSet, metadataTargetBrokers, clientId, 10000).topicsMetadata
    val partitions = topicsMetadata.head.partitionsMetadata.map(_.partitionId)
    partitions
  }

  /**
    * function : get consumer group offset dir from zookeeper
    * @param topicDirs
    * @return offset dir in zk
    */
  private def getZkOffsetDir(topicDirs:ZKGroupTopicDirs):String = {
    topicDirs.consumerOffsetDir
  }

  /**
    * function : judge path whether has children node
    * @param zkClient
    * @param topicDirs
    * @return
    */
  def hasChildrenPath(zkClient:ZkClient,topicDirs:ZKGroupTopicDirs) : Boolean = {
    val path = getZkOffsetDir(topicDirs)
    zkClient.countChildren(path) match {
      case 0 => false
      case _ => true
    }
  }

  /**
    * function : get partition use able offset
    *
    * get EarliestTime offset in broker
    * get LatestTime offset in broker
    * get offset we keeped in zookeeper
    * compare the zkOffset with EarliestTime offset and LatestTime offset
    * and get the useful offset
    *
    * @param zkClient
    * @param zkClientUTF8
    * @param brokers
    * @param groupId
    * @param topic
    * @return use able offsets
    */
  def getOffSet(zkClient:ZkClient, zkClientUTF8:ZkClient, brokers:String, groupId:String, topic:String,topicDirs:ZKGroupTopicDirs) : Map[TopicAndPartition, Long] = {

    var fromOffsets: Map[TopicAndPartition, Long] = Map()

    val topicSet = Set(topic)
    val clientId = "GetOffsetShell"
    val metadataTargetBrokers = ClientUtils.parseBrokerList(brokers)
    val topicsMetadata = ClientUtils.fetchTopicMetadata(topicSet, metadataTargetBrokers, clientId, 10000).topicsMetadata

    //get offset dir
    val offsetDir = getZkOffsetDir(topicDirs)
    //get topic all partitions
    val partitions = getPartitions(brokers,topic)

    partitions.foreach(
      partition => {
        val partitionMetadataOpt = topicsMetadata.head.partitionsMetadata.find(_.partitionId == partition)
        partitionMetadataOpt match {
          case Some(metadata) => {
            metadata.leader match {
              case Some(leader) => {

                //get offset from zk
                val dir = offsetDir+"/"+partition
                val offset = zkClient.readData[String](dir)
                val zkPartitionOffset = offset match {
                  case null => 0
                  case _ => {
                    var offsetTmp = 0L
                    try {
                      // 上次停下的位移
                      offsetTmp = offset.toLong
                    } catch { // 异常时使用UTF-8编码读取
                      case e : java.lang.NumberFormatException =>
                        offsetTmp = zkClientUTF8.readData[String](dir, true).toLong
                    }
                    offsetTmp
                  }
                }

                val topicAndPartition = TopicAndPartition(topic, partition)
                val consumer = new SimpleConsumer(leader.host, leader.port, 30000, 64*1024, clientId)

                //get max offset from broker
                val requestMax = OffsetRequest(Map(topicAndPartition -> PartitionOffsetRequestInfo(OffsetRequest.LatestTime, 1)))
                val maxOffset = consumer.getOffsetsBefore(requestMax).partitionErrorAndOffsets(topicAndPartition).offsets

                //get min offset from broker
                val requestMin = OffsetRequest(Map(topicAndPartition -> PartitionOffsetRequestInfo(OffsetRequest.EarliestTime, 1)))
                val minOffset = consumer.getOffsetsBefore(requestMin).partitionErrorAndOffsets(topicAndPartition).offsets

                //make sure offset in [minOffset,maxOffset]
                val resultOffset = Math.max(Math.min(zkPartitionOffset, maxOffset.head), minOffset.head)

                println("====zk offset is "+zkPartitionOffset + " ===broker min offset is "+minOffset.head+" ======broker max offset is "+maxOffset.head)

                fromOffsets += (topicAndPartition -> resultOffset)
              }
              case None => System.err.println("Error: partition %d does not have a leader. Skip getting offsets".format(partition))
            }
          }
          case None => System.err.println("Error: partition %d does not exist".format(partition))
        }
      }
    )
    fromOffsets
  }

}
