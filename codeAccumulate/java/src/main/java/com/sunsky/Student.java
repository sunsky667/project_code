package com.sunsky;

public class Student {

    private String name = genName();
    private int age;

    public String genName(){
        System.out.println("==================");
        return "aaa";
    }

    public static void main(String[] args) {
        Student student = new Student();
    }

}
