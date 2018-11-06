package com.sunsky.designModel.simpleFactory.impl;

import com.sunsky.designModel.simpleFactory.Car;

public class Benz implements Car{
    public void start() {
        System.out.println("Benz start");
    }

    public void run() {
        System.out.println("Benz run");
    }
}
