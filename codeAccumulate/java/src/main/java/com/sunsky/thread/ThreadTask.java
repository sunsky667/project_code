package com.sunsky.thread;

/**
 * Created by sunsky on 2017/6/28.
 */
public class ThreadTask implements Runnable{

    private int num;

    public ThreadTask(int num){
        this.num = num;
    }

    public void run() {
        int count = 0;
        while (true){
            System.out.println("this is fucking thread "+num +" count "+count++);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
