package com.sunsky.thread.interrupt;

public class InterruptExample {

    public static void main(String[] args) {
        SleepThread t1 =new SleepThread();
        SleepThread t2 = new SleepThread();

        t1.time = 1000;
        t2.time = 2000;

        t1.other = t2;

        t1.start();
        t2.start();

    }
}

class SleepThread extends Thread{

    int time;
    SleepThread other;

    public void run(){
        System.out.println(getName() +" start ");
        try{
            sleep(time);
            System.out.println(getName()+ " 大梦谁先醒");
            //在t1中叫醒t2
            other.interrupt();
        }catch(InterruptedException e){
            e.printStackTrace();
            System.out.println(getName()+ " 不高兴");
        }
        System.out.println(getName()+ "结束");
    }
}
