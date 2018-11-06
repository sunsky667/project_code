package com.migu.kafka.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressWarnings("all")
public class MessageQueue {
    private static BlockingQueue<String> queue = new LinkedBlockingDeque<String>(300000);
    public static BlockingQueue<String> getQueue() {
        return queue;
    }
}
