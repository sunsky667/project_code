package com.sunsky.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndRunnable {

    public static void main(String[] args) throws Exception{

        ExecutorService executors = Executors.newFixedThreadPool(3);

//        System.out.println("start submit task");
//
//        for (int i=0;i<20;i++){
//            Task task = new Task(i);
//            executors.submit(task);
//        }
//
//        System.out.println("submit finished ");

        System.out.println("start submit task");
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        for (int i=0;i<20;i++){
            CallTask task = new CallTask(i);
            Future<String> future = executors.submit(task);
            resultList.add(future);
        }

        System.out.println("submit finished ");

        for(Future<String> future : resultList){
            System.out.println(future.get());
        }

    }

    private static class Task implements Runnable{

        private int taskNum;

        public Task(int treadNum){
            this.taskNum = treadNum;
        }

        public void run() {
            System.out.println("start run task "+taskNum);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(taskNum+" task run finished");
        }
    }

    private static class CallTask implements Callable{

        private int taskNum;

        public CallTask(int treadNum){
            this.taskNum = treadNum;
        }

        public Object call() throws Exception {
            System.out.println("start run task "+taskNum);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(taskNum+" task run finished");
            return taskNum+" task run finished , fuck ....";
        }
    }
}
