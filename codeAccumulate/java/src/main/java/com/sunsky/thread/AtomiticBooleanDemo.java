package com.sunsky.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomiticBooleanDemo {

    //for BarWorker
    private static boolean exists = false;
    //for BarWorker2
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
//        new BarWorker("bar1").start();
//        new BarWorker("bar2").start();
        new BarWork2("bar1").start();
        new BarWork2("bar2").start();
    }

    /**
     * 由于没有用AtomicBoolean原子性操作
     * 两个线程可以同时进入（没有原子性）
     */
    private static class BarWorker extends Thread {
        private String name;
        public BarWorker(String name){
            this.name = name;
        }

        @Override
        public void run() {
            if(!exists){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    // do nothing
                }
                exists = true;
                System.out.println(name + " enter");
                try {
                    System.out.println(name + " working");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    // do nothing
                }
                System.out.println(name + " leave");
                exists = false;
            }else {
                System.out.println(name + " give up");
            }
        }
    }

    /**
     * 仅仅一个线程进行工作，
     * 因为exists.compareAndSet(false, true)提供了原子性操作，
     * 比较和赋值操作组成了一个原子操作, 中间不会提供可乘之机。
     */
    private static class BarWork2 extends Thread{

        private String name;

        BarWork2(String name){
            this.name = name;
        }

        @Override
        public void run() {
            if(atomicBoolean.compareAndSet(false,true)){
                System.out.println(name+" enter");
                try {
                    System.out.println(name + " working");
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(name + " leave");
                atomicBoolean.set(false);
            }else{
                System.out.println(name + " give up");
            }
        }
    }
}
