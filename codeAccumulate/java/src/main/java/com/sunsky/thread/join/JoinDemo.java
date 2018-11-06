package com.sunsky.thread.join;

public class JoinDemo {

    /**
     * join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
         程序在main线程中调用thread线程的join方法，则main线程放弃cpu控制权，并返回thread线程继续执行,
         直到线程thread执行完毕,所以结果是t1线程执行完后，才到主线程执行，
         相当于在main线程中同步thread线程，thread执行完了，main线程才有执行的机会

         当join方法被注释后打印的结果为：
         main start running
         main stop running
         Thread-1 start running
         Thread-0 start running
         Thread-1 stop running
         Thread-0 stop running

         当join方法存在时打印结果为：
         main start running
         Thread-0 start running
         Thread-0 stop running
         Thread-1 start running
         Thread-1 stop running
         main stop running
     */
    public static void main(String[] args) throws Exception{

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start running");
                try {
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" stop running");
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start running");
                try {
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" stop running");
            }
        });

        System.out.println(Thread.currentThread().getName()+" start running");
        thread.start();
        thread.join();
        thread1.start();
        thread1.join();
        System.out.println(Thread.currentThread().getName()+" stop running");
    }

}
