package com.sunsky.thread.join;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        T1 t1 = new T1();
        T2 t2 = new T2();
        t1.list = list;
        t2.list = list;
        t2.t1 = t1;

        t1.start();
        t2.start();
    }
}


class T1 extends Thread{
    List<Integer> list;
    public void run(){
        for(int i = 0;i<10;i++){
            list.add((int)(Math.random()*100));
        }
        try{
            sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("T1: "+ list);
    }

}


class T2 extends Thread{
    List<Integer> list;
    T1 t1;
    public void run(){
        try{
            t1.join();  //当A线程执行到了B线程的.join()方法时，A就会等待。等B线程都执行完，A才会执行。
            Collections.sort(list);
            System.out.println("T2 : " +list);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}