package com.sunsky.thread.communication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Producer producer = new Producer(1);
        Producer producer1 = new Producer(2);
        Comsumer comsumer1 = new Comsumer(1);
        Comsumer comsumer2 = new Comsumer(2);

        executorService.submit(producer);
        executorService.submit(producer1);
        executorService.submit(comsumer1);
        executorService.submit(comsumer2);

    }
}
