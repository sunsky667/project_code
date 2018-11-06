package com.migu.kafka.send;

import com.migu.kafka.producer.KafkaProducer;
import com.migu.kafka.queue.MessageQueue;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class KafkaSender implements Runnable{

    private static Producer<String,String> producer = KafkaProducer.getInstance().getProducer();

    private static BlockingQueue<String> messageQueue = MessageQueue.getQueue();

    private static List<KeyedMessage<String, String>> messages = new ArrayList<KeyedMessage<String, String>>();

    public void sendMessage(String message) throws Exception{
        while(true){
            if(messageQueue.size() >= 200000){
                Thread.sleep(500);
            }else{
                messageQueue.put(message);
                break;
            }
        }
    }

    public void run() {
        try {
            String line = null;
            while (true){

                if((line = messageQueue.poll(10, TimeUnit.MILLISECONDS)) != null){
                    KeyedMessage<String,String> keyedMessage = new KeyedMessage<String, String>("test","",line);
                    messages.add(keyedMessage);
                    if(messages.size() >= 1000){
                        System.out.println("==========================================");
                        producer.send(messages);
                        messages.clear();
                    }
                }else{
                    if(messages.size() > 0){
                        producer.send(messages);
                        messages.clear();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
