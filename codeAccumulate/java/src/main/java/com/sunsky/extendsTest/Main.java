package com.sunsky.extendsTest;

public class Main {
    public static void main(String[] args) {
        Child.ddd("aaa");
        Child child = new Child();
        child.a();
        child.b();

        for(int i=0;i<30;i++){
            judge(i);
        }
    }

    public static void judge(int i){
        if(i == 10){
            return;
        }
        System.out.println("==="+i);
    }
}
