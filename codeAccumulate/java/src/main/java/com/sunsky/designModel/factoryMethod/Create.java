package com.sunsky.designModel.factoryMethod;

public abstract class Create {

    public abstract Car createCar(String name);

    public Car factoryMethod(String name){
        Car car = this.createCar(name);
        car.run("120");
        return car;
    }
}
