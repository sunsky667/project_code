package com.sunsky;

public class ExceptionTest {
    public static void main(String[] args) {

        try {
            ExceptionTest exceptionTest = new ExceptionTest();
            exceptionTest.thr();
        }catch (Exception e){
            System.out.println("main catch");
            e.printStackTrace();
        }

    }

    public void thr(){
        try{
            System.out.println("thr start");
            int a = 1;
            int b = 0;
            int c = a/b;
            System.out.println(c);
            System.out.println("thr end");
        }catch (Exception e){
            System.out.println("thr catch");
            e.printStackTrace();
            throw new RuntimeException("heheh");
        }finally {
            System.out.println("this is finally");
        }
    }
}
