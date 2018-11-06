package com.sunsky.thread.communication;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    private Integer producerId;

    private BlockingQueue<String> blockingQueue = Queue.blockingQueue();

    public Producer(Integer producerId){
        this.producerId = producerId;
    }



    public void run() {
        while (true){
            for(int i =0 ;i < 1000 ; i++){
                try {
                    blockingQueue.put("this message product by producer : " + producerId + " ===> the message is : " +i);
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("===== producer finish one roll====");
        }

    }
}
