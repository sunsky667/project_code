package com.sunsky.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * kafka高级消费者
 * 利用zookeeper的配置信息来消费kafka数据
 */
public class KafkaConsumer {

    private ConsumerConnector consumerConnector ;
    private String topic;
    private ExecutorService executorService;

    /**
     * construction method
     *
     * 1.create kafka consumer connector
     * 2.init topic
     *
     * @param zookeeper
     * @param topic
     * @param groupId
     */
    public KafkaConsumer(String zookeeper, String topic, String groupId){
        // 创建Connector，注意下面对conf的配置
        this.consumerConnector = Consumer.createJavaConsumerConnector(createConsumerConfig(zookeeper,groupId));
        this.topic = topic;
    }

    /**
     * create ConsumerConfig
     * for construct kafka consumer connection
     * @param zookeeper
     * @param groupId
     * @return
     */
    private static ConsumerConfig createConsumerConfig(String zookeeper,String groupId){
        Properties properties = new Properties();
        properties.put("zookeeper.connect",zookeeper);
        properties.put("group.id",groupId);
        properties.put("zookeeper.session.timeout.ms", "4000");
        properties.put("zookeeper.sync.time.ms", "2000");
        properties.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(properties);
    }

    /**
     * 1.new executor pool
     * 2.get KafkaStream
     * 3.call thread DealKafkaStream object to deal KafkaStream
     * @param numberThreads
     */
    public void startRecieve(int numberThreads){ //创建并发的consumers

        //
        this.executorService = Executors.newFixedThreadPool(numberThreads);

        Map<String,Integer> topicCountMap = new HashMap<String, Integer>();
        // 描述读取哪个topic，需要几个线程读，由于topic消费是按分区来消费的，每个分区不能多线程消费，故这个值最好是设置为分区大小
        topicCountMap.put(this.topic,new Integer(numberThreads));

        //通过consumerConnector连接传入一个map(topic, #streams)来获取到一个map(topic, list of  KafkaStream)的结果
        //每个线程对应于一个KafkaStream
        Map<String,List<KafkaStream<byte[],byte[]>>> resultMap = consumerConnector.createMessageStreams(topicCountMap);

        //根据topic来取得这个topic对应的List<KafkaStream<byte[],byte[]>>
        List<KafkaStream<byte[],byte[]>> streams = resultMap.get(this.topic);
        System.out.println("=============================size======================="+streams.size());

        //处理KafkaStream<byte[],byte[]>，主要是byte数组解析为字符串
        //KafkaStream也是可迭代的
        for(KafkaStream<byte[],byte[]> stream : streams){

            if(stream != null){
                executorService.submit(new DealKafkaStream(stream));
            }

        }
    }

    /**
     * shutdown
     */
    public void shutdown() {
        if (consumerConnector != null)
            consumerConnector.shutdown();
        if (executorService != null)
            executorService.shutdown();
    }

    public static void main(String[] args){

        String zookeeper = "192.168.80.128:2181";
        String topic = "test";
        String groupId = "test_ddd";

        KafkaConsumer kafkaSimpleConsumer = new KafkaConsumer(zookeeper,topic,groupId);

        System.out.println(kafkaSimpleConsumer.consumerConnector);
        kafkaSimpleConsumer.startRecieve(3);

        //kafkaSimpleConsumer.shutdown();
    }


}
