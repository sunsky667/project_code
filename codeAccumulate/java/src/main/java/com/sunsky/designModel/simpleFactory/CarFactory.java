package com.sunsky.designModel.simpleFactory;

public class CarFactory {

    public static Car getInstance(String clazz){
        Car car = null;
        try {
            car = (Car) Class.forName("com.sunsky.designModel.simpleFactory.impl."+clazz).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return car;
    }
}
