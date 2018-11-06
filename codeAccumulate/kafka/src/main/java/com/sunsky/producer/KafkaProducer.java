package com.sunsky.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaProducer {

    //private Producer<String,String> producer;
    private ProducerConfig producerConfig;

    private ExecutorService executorService;

    public KafkaProducer(){
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
    }



    public void run() {

        Producer<String, String> producer = new Producer<String, String>(this.producerConfig);
        /**
    //		for(int i=0; i<10; i++) {
    //			String sLine = "I'm number " + i;
    //			KeyedMessage<String, String> msg = new KeyedMessage<String, String>("group1", sLine);
    //			producer.send(msg);
    //		}
         **/
        for(int i = 1; i <= 3; i++){ //往3个分区发数据
            List<KeyedMessage<String, String>> messageList = new ArrayList<KeyedMessage<String, String>>();
            for(int j = 0; j < 6; j++){ //每个分区6条讯息
                messageList.add(new KeyedMessage<String, String>
                        //String topic, String partition, String message
                        ("test", "partition[" + i + "]", "partition[" + i + "]"+"message[The " + j + " message]"));
            }
            producer.send(messageList);
        }


        //producer = new Producer<String, String>(this.producerConfig);
//        this.executorService = Executors.newFixedThreadPool(3);
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
//        executorService.submit(new SendMessage(new Producer<String, String>(this.producerConfig)));
    }

    public static void main(String[] args){
        KafkaProducer kafkaProducer = new KafkaProducer();
        kafkaProducer.run();
    }

}
