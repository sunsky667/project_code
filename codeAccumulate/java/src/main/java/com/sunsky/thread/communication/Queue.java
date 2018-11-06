package com.sunsky.thread.communication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Queue {

    public static BlockingQueue<String> blockingQueue ;

    private static Queue queue;

    public synchronized static Queue singleton(){
        if(queue == null ){
            queue = new Queue();
        }
        return queue;
    }

    public synchronized static BlockingQueue<String> blockingQueue(){
        if(blockingQueue == null ){
            blockingQueue = new LinkedBlockingQueue<String>(1000);
        }
        return blockingQueue;
    }
}
