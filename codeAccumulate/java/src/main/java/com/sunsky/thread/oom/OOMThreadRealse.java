package com.sunsky.thread.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @vm jvm args : args:-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails
 *
 * 从日志可以看出在thead-0发生OOM之后，thread-1仍旧能够继续申请内存工作。
 * 使用jconsole监控发现，thread-0开始慢慢把heap压满，发生OOM之后神奇的事情发生了，heap基本上被清空了，
 * 通过查看jconsole看到的线程信息，发现没有thead-0线程了。
 * 这就很明确了，因为thead-0没有捕获该异常，跳出了while循环，
 * 导致thead-0线程运行结束，该线程持有的对象也就能被释放了
 *
 * 发生OOM之后会不会影响其他线程正常工作需要具体的场景分析。但是就一般情况下，
 * 发生OOM的线程都会终结（除非代码写的太烂），该线程持有的对象占用的heap都会被gc了，释放内存。
 *
 * 因为发生OOM之前要进行gc，就算其他线程能够正常工作，也会因为频繁gc产生较大的影响。
 */
public class OOMThreadRealse {

    static class Thead0 implements Runnable{
        @Override
        public void run() {
            List<byte[]> byteList = new ArrayList<>();
            while (true){
                byteList.add(new byte[1024]);
            }
        }
    }

    static class Thead1 implements Runnable{

        @Override
        public void run() {
            while (true){
                List<byte[]> byteList = new ArrayList<>();
                try {
                    byteList.add(new byte[1024]);
                    Thread.sleep(1000);
                    System.out.println("========================");
                }catch (Exception e){

                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(2000);
        new Thread(new Thead0(),"thread-0").start();
        new Thread(new Thead1(),"thread-1").start();
    }

}




