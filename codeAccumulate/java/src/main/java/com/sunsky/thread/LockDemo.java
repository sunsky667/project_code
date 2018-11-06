package com.sunsky.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private static List<Integer> list = new ArrayList<>();
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        final LockDemo lockDemo = new LockDemo();
        new Thread(){
            @Override
            public void run() {
                lockDemo.insert();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                lockDemo.insert();
            }
        }.start();
    }

    public void insert(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"get lock ");

            for(int i=0;i<5 ;i++){
                list.add(i);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(Thread.currentThread().getName()+"release lock");
            lock.unlock();
        }
    }

}
