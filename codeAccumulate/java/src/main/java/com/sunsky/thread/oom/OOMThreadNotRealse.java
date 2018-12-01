package com.sunsky.thread.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @vm jvm args : args:-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails
 *
 * 我们知道java对象基本上都是在堆上分配（有特殊情况下，不在我们讨论的范围内）。
 * 小对象都是直接在Eden区域中分配。如果此时内存不够，就会发生young gc，如果释放之后还是内存不够，
 * 此时jvm会进行full gc。如果发生full gc之后内存还是不够，此时就会抛出“java.lang.OutOfMemoryError: Java heap space”。
 * 大对象jvm会直接在old 区域中申请，但是和小对象分配的原理类似。
 *
 * 一般情况下，java对象内存分配跟线程无关（TLAB例外），能够申请成功至于当前只和当前heap空余空间有关。
 *
 * 发生OOM之后会不会影响其他线程正常工作需要具体的场景分析。但是就一般情况下，
 * 发生OOM的线程都会终结（除非代码写的太烂），该线程持有的对象占用的heap都会被gc了，释放内存。
 *
 * 因为发生OOM之前要进行gc，就算其他线程能够正常工作，也会因为频繁gc产生较大的影响。
 */
public class OOMThreadNotRealse {

    static class Thead0 implements Runnable{
        @Override
        public void run() {
            List<byte[]> list = new ArrayList<>();
            try {
                while (true){
                    byte[] bytes = new byte[1024];
                    list.add(bytes);
                }
            }catch (Throwable throwable){
                System.out.println("===== "+Thread.currentThread().getName()+" oom");
            }
            try {
                Thread.sleep(30000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    static class Thead1 implements Runnable{
        @Override
        public void run() {
            while (true){
                List<byte[]> list = new ArrayList<>();
                try {
                    byte[] bytes = new byte[1024];
                    list.add(bytes);
                    Thread.sleep(1000);
                    System.out.println("================================");
                }catch (Exception e){

                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread.sleep(2000);
        new Thread(new Thead0(),"thread 0").start();
        new Thread(new Thead1(),"thread 1").start();
    }
}




