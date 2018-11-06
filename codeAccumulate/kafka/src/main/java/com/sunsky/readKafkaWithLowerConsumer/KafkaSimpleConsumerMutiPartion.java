package com.sunsky.readKafkaWithLowerConsumer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.cluster.Broker;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;

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
                    //System.out.println("==========================================partition id=========================="+partitionMetadata.partitionId());
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
        System.out.println(String.format("======================= Leader for topic %s, partition %d is %s:%d", topic, partition, leader.host(), leader.port()));
        return leader;
    }

    /**
     * 消费
     */
    public void consume(){

        ExecutorService executorService = Executors.newFixedThreadPool(80);
        String brokerHosts = "10.200.63.52:9092,10.200.63.53:9092,10.200.63.54:9092,10.200.63.55:9092,10.200.63.56:9092,10.200.63.57:9092,10.200.63.58:9092:10.200.63.59:9092,10.200.63.60:9092";
        String topic = "pub_visit_topic";
        int partition = 80;

        for(int i = 0 ; i < partition; i++){
            Broker leader = findLeader(brokerHosts,topic,i);
            String clientId = "Client_" + topic + "_" + i;
            Consumers consumers = new Consumers(leader.host(),leader.port(),clientId,topic,i);
            executorService.submit(consumers);
        }

    }

}
