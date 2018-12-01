package com.sunsky.thread.oom;

import java.util.ArrayList;
import java.util.List;

public class OOMThread {

    public static void main(String[] args) throws Exception {
        Thread.sleep(2000);
        new Thread(new Thead0(),"thread-0").start();
        new Thread(new Thead1(),"thread-1").start();
    }

}

class Thead0 implements Runnable{
    @Override
    public void run() {
        List<byte[]> byteList = new ArrayList<>();
        while (true){
            byteList.add(new byte[1024]);
        }
    }
}

class Thead1 implements Runnable{

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


