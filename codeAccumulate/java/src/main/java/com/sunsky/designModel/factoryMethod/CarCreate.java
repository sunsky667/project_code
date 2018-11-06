package com.sunsky.designModel.factoryMethod;

public class CarCreate extends Create {
    public Car createCar(String name) {
        Car car = null;
        try {
            car = (Car) Class.forName("com.sunsky.designModel.factoryMethod."+name).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return car;
    }
}
