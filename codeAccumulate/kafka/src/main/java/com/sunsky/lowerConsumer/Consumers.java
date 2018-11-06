package com.sunsky.lowerConsumer;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;

public class Consumers implements Runnable {

    private String host;
    private int port;
    private String clientId;
    private String topic;
    private int partition;
    private SimpleConsumer simpleConsumer;

    public Consumers(String host,int port,String clientId,String topic,int partition){
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        this.topic = topic;
        this.partition = partition;
        this.simpleConsumer = new SimpleConsumer(this.host,this.port,100000,64*1024,this.clientId);
    }

    public void run() {

        long startOffset = 1;
        int fetchSize = 10000;

        while (true){

            if(simpleConsumer == null ){
                System.out.println("========== consumer is null==========");
                simpleConsumer = new SimpleConsumer(this.host, this.port, 100000, 64 * 1024, this.clientId);
            }

            long offset = startOffset;

            FetchRequest fetchRequest = new FetchRequestBuilder()
                    .clientId(clientId)
                    .addFetch(topic, partition, startOffset, fetchSize)
                    .build();

            FetchResponse fetchResponse = simpleConsumer.fetch(fetchRequest);

            if(fetchResponse.hasError()){
                short code = fetchResponse.errorCode(topic, partition);
                System.out.println("Error fetching data from the Broker:"  + " Reason: " + code);
                //offset -= 1;
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                continue;
            }
            ByteBufferMessageSet messageSet = fetchResponse.messageSet(topic, partition);
            for(MessageAndOffset messageAndOffset : messageSet){
                Message message = messageAndOffset.message();
                ByteBuffer payload = message.payload();
                byte[] bytes = new byte[payload.limit()];
                payload.get(bytes);
                String msg = new String(bytes);
                offset = messageAndOffset.offset();
                System.out.println("partition : " + partition + ", offset : " + offset + "  mess : " + msg);
            }
            startOffset = offset + 1;
        }
    }
}
