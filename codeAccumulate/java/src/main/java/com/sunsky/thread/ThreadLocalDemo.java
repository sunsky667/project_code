package com.sunsky.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 所以ThreadLocal与线程同步机制不同，
 * 线程同步机制是多个线程共享同一个变量，
 * 而ThreadLocal是为每一个线程创建一个单独的变量副本，
 * 故而每个线程都可以独立地改变自己所拥有的变量副本，
 * 而不会影响其他线程所对应的副本。
 * 可以说ThreadLocal为多线程环境下变量问题提供了另外一种解决思路。
 */
public class ThreadLocalDemo {

    /**
     * ThreadLocal定义了四个方法：
     * get()：返回此线程局部变量的当前线程副本中的值。
     * initialValue()：返回此线程局部变量的当前线程的“初始值”。
     * remove()：移除此线程局部变量当前线程的值。
     * set(T value)：将此线程局部变量的当前线程副本中的值设置为指定值。
     */
    public static ThreadLocal<Integer> seqCount = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int nextSeq(){
        seqCount.set(seqCount.get()+1);
        return  seqCount.get();
    }

    public static void main(String[] args) {

        /**
         * ThreadLocal实例本身是不存储值，它只是提供了一个在当前线程中找到副本值得key。
         * 是ThreadLocal包含在Thread中，而不是Thread包含在ThreadLocal中。
         */

        System.out.println(Thread.currentThread().getName());

        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        SeqThread seqThread1 = new SeqThread(threadLocalDemo);
        SeqThread seqThread2 = new SeqThread(threadLocalDemo);
        SeqThread seqThread3 = new SeqThread(threadLocalDemo);
        SeqThread seqThread4 = new SeqThread(threadLocalDemo);

        seqThread1.start();
        seqThread2.start();
        seqThread3.start();
        seqThread4.start();

        /**
         * 由于线程池是线程复用的，复用的线程同时也会复用ThreadLocal
         * 一个线程被复用几次，ThreadLocal就会被复用几次
         */
//        ExecutorService service = Executors.newFixedThreadPool(3);
//        SeqThreadRun seqThreadRun1 = new SeqThreadRun(threadLocalDemo);
//        SeqThreadRun seqThreadRun2 = new SeqThreadRun(threadLocalDemo);
//        SeqThreadRun seqThreadRun3 = new SeqThreadRun(threadLocalDemo);
//        SeqThreadRun seqThreadRun4 = new SeqThreadRun(threadLocalDemo);
//
//        service.execute(seqThreadRun1);
//        service.execute(seqThreadRun2);
//        service.execute(seqThreadRun3);
//        service.execute(seqThreadRun4);
    }

    private static class SeqThread extends Thread{

        private ThreadLocalDemo threadLocalDemo;

        SeqThread(ThreadLocalDemo threadLocalDemo){
            this.threadLocalDemo = threadLocalDemo;
        }

        @Override
        public void run() {
            for(int i=0;i<3;i++){
                System.out.println(Thread.currentThread().getName()+" seqCount : "+threadLocalDemo.nextSeq());

            }
        }
    }

    private static class SeqThreadRun implements Runnable{

        private ThreadLocalDemo threadLocalDemo;

        SeqThreadRun(ThreadLocalDemo threadLocalDemo){
            this.threadLocalDemo = threadLocalDemo;
        }

        @Override
        public void run() {
            for(int i=0;i<3;i++){
                System.out.println(Thread.currentThread().getName()+" seqCount : "+threadLocalDemo.nextSeq());
            }
        }
    }
}
