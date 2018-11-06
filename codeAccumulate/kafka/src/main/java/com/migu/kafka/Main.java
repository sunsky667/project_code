package com.migu.kafka;

import com.migu.kafka.send.KafkaSender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(3);
        KafkaSender kafkaSender = new KafkaSender();

        service.submit(kafkaSender);
        int i = 0;
        while (true){
            kafkaSender.sendMessage("aaaaa"+i);
            i++;
        }
    }
}
