package com.sunsky.thread.communication;

import java.util.concurrent.BlockingQueue;

public class Comsumer implements Runnable{

    private Integer treadId;
    private BlockingQueue<String> blockingQueue = Queue.blockingQueue();

    public Comsumer(Integer treadId){
        this.treadId = treadId;
    }

    public void run() {
        while (true){
            try {
                String value = blockingQueue.take();
                System.out.println("consumerId : " + treadId + " ===>  get : " + value);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
