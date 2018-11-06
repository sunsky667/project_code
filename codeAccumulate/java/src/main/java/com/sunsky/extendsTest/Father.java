package com.sunsky.extendsTest;

public class Father {

    public static void ddd(String sd){
        System.out.println("====="+sd);
    }

    public void a(){
        System.out.println("father a method");
    }

    public void b(){
        a();
        System.out.println("father b method");
    }

}
