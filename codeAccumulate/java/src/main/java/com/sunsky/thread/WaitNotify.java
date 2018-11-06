package com.sunsky.thread;

public class WaitNotify extends Thread{
    public static void main(String[] args) throws Exception{
        WaitNotify waitNotify = new WaitNotify();
        waitNotify.start();
        //waitNotify.notify();  //notity和wait必须在synchronized里面，Exception in thread "main" java.lang.IllegalMonitorStateException

        Thread.sleep(3000);
        waitNotify.wake();

    }

    @Override
    public void run() {
        System.out.println("running !!!!");
        dosome();
    }

    public synchronized void dosome(){

        try {
            System.out.println("start");
            wait();
            System.out.println("end");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public synchronized void wake(){
        try {
          notify();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
