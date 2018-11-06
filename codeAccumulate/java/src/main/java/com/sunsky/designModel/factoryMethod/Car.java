package com.sunsky.designModel.factoryMethod;

public abstract  class Car {

    public String carName;

    public void run(String voice){
        System.out.println("run fast "+voice);
    }

    public void say(){
        System.out.println("create "+carName+" success");
    }
}
