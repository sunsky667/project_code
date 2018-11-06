package com.sunsky.consumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class DealKafkaStream implements Runnable{

    private KafkaStream<byte[],byte[]> stream;

    /**
     * construct method
     * @param stream
     */
    public DealKafkaStream(KafkaStream<byte[],byte[]> stream){
        this.stream = stream;
    }

    /**
     * thread to deal kafkaStream
     */
    public void run() {
        //我tm看他如果分区数和线程数不等的话，消费的数据是不是一致的
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while(it.hasNext()){
            try {
                String message = new String(it.next().message(),"utf-8").trim();
                System.out.println("message is =====>     "+message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
