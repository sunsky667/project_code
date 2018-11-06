package com.sunsky.annotation.method;

public class Manager {

    @MethodInfo(author = "somebody",date="20180801",description = "annotation method test")
    public void method(){
        System.out.println("exec manager method");
    }

}
