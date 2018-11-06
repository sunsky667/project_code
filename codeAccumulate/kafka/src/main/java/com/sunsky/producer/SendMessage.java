package com.sunsky.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import java.util.ArrayList;
import java.util.List;

public class SendMessage implements Runnable{

    private Producer producer;


    SendMessage(Producer producer){
        this.producer = producer;
    }

    /**
     * 发送消息操作步骤（批量发送）
     * 1.建立一个List<KeyedMessage<String, String>>类型的列表（用于存放要批量发送的消息）
     * 2.kafka生成者发送的消息是KeyedMessage<String, String>类型的，将新建的消息添加到List消息列表中
     * 3.调用producer.send方法，一次发送整个List消息列表
     */
    public void run() {
        List<KeyedMessage<String, String>> messageList = new ArrayList<KeyedMessage<String, String>>();
        for(int j = 0; j < 100; j++){
            messageList.add(new KeyedMessage<String, String>
                    //String topic, String partition, String message
                    ("test", "partition[" + j%6 + "]", "message[The " + j + " message]"));
        }
        //producer send message list, message list contents KeyedMessage
        producer.send(messageList);
        System.out.println("message send finished");
        producer.close();
    }
}
