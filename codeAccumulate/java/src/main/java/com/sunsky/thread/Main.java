package com.sunsky.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sunsky on 2017/6/28.
 */
public class Main {
    public static void main(String[] args){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadTask task = new ThreadTask(1);
        ThreadTask task1 = new ThreadTask(2);
        pool.submit(task);
        pool.submit(task1);

    }
}
