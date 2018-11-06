package com.sunsky.lowerConsumer;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.cluster.Broker;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaSimpleConsumerMutiPartion {

    public static void main(String[] args){
        KafkaSimpleConsumerMutiPartion kafkaSimpleConsumerMutiPartion = new KafkaSimpleConsumerMutiPartion();
        kafkaSimpleConsumerMutiPartion.consume();
    }

    /**
     * 找到指定分区的元数据
     *
     * @param brokerHosts broker地址，格式为：“host1:port1,host2:port2,host3:port3”
     * @param topic topic
     * @param partition 分区
     * @return 指定分区的元数据
     */
    private PartitionMetadata findPartitionMetadata(String brokerHosts, String topic, int partition){

        //指定分区的元数据
        PartitionMetadata backMetadata = null;

        for(String brokerHost : brokerHosts.split(",")){

            SimpleConsumer simpleConsumer = null;
            //get broker ip and host
            String[] ipAndHost = brokerHost.split(":");
            //create a SimpleConsumer
            simpleConsumer = new SimpleConsumer(ipAndHost[0],Integer.parseInt(ipAndHost[1]),100000,64*1024,"leaderLookup");
            //convert topic string to list
            List<String> topics = Collections.singletonList(topic);
            //user topics to create TopicMetadataRequest
            TopicMetadataRequest request = new TopicMetadataRequest(topics);
            //user simpleConsumer send the TopicMetadataRequest and return TopicMetadataResponse
            TopicMetadataResponse response = simpleConsumer.send(request);
            //get TopicMetadata from response
            List<TopicMetadata> topicMetadatas = response.topicsMetadata();

            for(TopicMetadata topicMetadata : topicMetadatas){
                //get PartitionMetadata from topicMetadata
                for(PartitionMetadata partitionMetadata : topicMetadata.partitionsMetadata()){
                    //judge partition id from partition metadata with partition id we want
                    System.out.println("==========================================partition id=========================="+partitionMetadata.partitionId());
                    if(partitionMetadata.partitionId() == partition){
                        backMetadata = partitionMetadata;
                    }
                }
            }

            if(simpleConsumer != null){
                simpleConsumer.close();
            }
        }

        return backMetadata;
    }


    /**
     * 找到指定分区的leader broker
     *
     * @param brokerHosts broker地址，格式为：“host1:port1,host2:port2,host3:port3”
     * @param topic topic
     * @param partition 分区
     * @return Broker
     */
    public Broker findLeader(String brokerHosts, String topic, int partition){
        Broker leader = findPartitionMetadata(brokerHosts,topic,partition).leader();
        System.out.println(String.format("Leader tor topic %s, partition %d is %s:%d", topic, partition, leader.host(), leader.port()));
        return leader;
    }

    /**
     * 根据时间戳找到某个客户端消费的offset
     *
     * @param consumer SimpleConsumer
     * @param topic topic
     * @param partition 分区
     * @param clientID 客户端的ID
     * @param whichTime 时间戳
     * @return offset
     */
    public long getLastOffset(SimpleConsumer consumer, String topic, int partition, String clientID, long whichTime) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo =
                new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientID);
        OffsetResponse response = consumer.getOffsetsBefore(request);
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }


    public void consume(){

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        String brokerHosts = "192.168.80.128:9092";
        String topic = "test";
        int partition = 3;

        for(int i = 0 ; i < partition; i++){
            Broker leader = findLeader(brokerHosts,topic,i);
            String clientId = "Client_" + "test" + "_" + i;
            Consumers consumers = new Consumers(leader.host(),leader.port(),clientId,topic,i);
            executorService.submit(consumers);
        }

    }

}
