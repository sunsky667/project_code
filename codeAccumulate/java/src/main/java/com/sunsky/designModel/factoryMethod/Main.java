package com.sunsky.designModel.factoryMethod;

public class Main {
    public static void main(String[] args){
        Create create = new CarCreate();
        Car benz = create.factoryMethod("Benz");
        benz.say();
    }
}
