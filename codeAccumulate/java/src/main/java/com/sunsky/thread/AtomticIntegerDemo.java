package com.sunsky.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomticIntegerDemo {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static int value = 0;

    public static void main(String[] args) throws Exception {
        //AtomicInteger这个类的存在是为了满足在高并发的情况下
        addAtomicInteger();
        Thread.sleep(3000);
        System.out.println("final result is : "+atomicInteger.get());

        //原始int高并发的情况相加，会丢失部分结果
//        intValueIncrement();
//        Thread.sleep(3000);
//        System.out.println("final value result is : "+value);
    }

    /**
     * AtomicInteger这个类的存在是为了满足在高并发的情况下,
     * 原生的整形数值自增线程不安全的问题。
     */
    private static void addAtomicInteger(){
        ExecutorService service = Executors.newFixedThreadPool(10000);
        for(int i=0;i<10000;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for(int j =0;j<4;j++){
                        System.out.println(atomicInteger.getAndIncrement());
                    }
                }
            });
        }
        service.shutdown();
    }


    /**
     * 若不用atomicInteger，最后的结果由于多线程的缘故(高并发的情况)，会比实际预期值小
     * 若使用了synchronized去做同步的话系统的性能将会大大下降
     * final value result is : 39993
     */
    private static void intValueIncrement(){
        ExecutorService service = Executors.newFixedThreadPool(10000);
        for(int i=0;i<10000;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<4;j++){
                        System.out.println(value++);
                    }
                }
            });
        }
        service.shutdown();
    }

}
