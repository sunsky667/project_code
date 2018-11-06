package com.sunsky.readKafkaWithLowerConsumer;

import java.nio.ByteBuffer;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.common.ErrorMapping;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;

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
        this.simpleConsumer = new SimpleConsumer(this.host,this.port,10000,64*1024,this.clientId);
    }

    public void run() {

    	//该地方需要根据实际的需要修改，取broker里最小的偏移量还是最大的偏移量
        long startOffset = KafkaUtil.getLastOffset(simpleConsumer, topic, partition, clientId, kafka.api.OffsetRequest.EarliestTime());
		int fetchSize = 10000;

        while (true){

            if(simpleConsumer == null ){
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


				if (code == ErrorMapping.OffsetOutOfRangeCode())  {
					// We asked for an invalid offset. For simple case ask for the last element to reset
					startOffset = KafkaUtil.getLastOffset(simpleConsumer,topic, partition,clientId,kafka.api.OffsetRequest.LatestTime());
					continue;
				}

				if (code == ErrorMapping.BrokerNotAvailableCode()){

				}

                System.out.println("Error fetching data from the Broker:"  + " Reason: " + code);

                try {
                    Thread.sleep(10000);
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
