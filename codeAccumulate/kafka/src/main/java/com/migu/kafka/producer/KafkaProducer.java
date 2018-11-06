package com.migu.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer {

    private static volatile KafkaProducer instance = null;
    private ProducerConfig producerConfig;
    private Producer<String,String> producer;

    private KafkaProducer(){

        Properties properties = new Properties();
        properties.put("zookeeper.connect", "192.168.80.128:2181");
        // 指定序列化处理类，默认为kafka.serializer.DefaultEncoder,即byte[]
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        // 同步还是异步，默认2表同步，1表异步。异步可以提高发送吞吐量，但是也可能导致丢失未发送过去的消息
        properties.put("producer.type", "sync");
        // 是否压缩，默认0表示不压缩，1表示用gzip压缩，2表示用snappy压缩。压缩后消息中会有头来指明消息压缩类型，故在消费者端消息解压是透明的无需指定。
        properties.put("compression.codec", "1");
        // 指定kafka节点列表，用于获取metadata(元数据)，不必全部指定
        properties.put("metadata.broker.list", "192.168.80.128:9092");

        this.producerConfig = new ProducerConfig(properties);
        this.producer = new Producer<String, String>(producerConfig);
    }

    public static KafkaProducer getInstance(){
        synchronized (KafkaProducer.class){
            if(instance == null){
                instance = new KafkaProducer();
            }
        }
        return instance;
    }

    public Producer<String,String> getProducer(){
        return this.producer;
    }

}
