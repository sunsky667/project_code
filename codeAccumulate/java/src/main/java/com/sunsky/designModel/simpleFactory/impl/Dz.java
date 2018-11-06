package com.sunsky.designModel.simpleFactory.impl;

import com.sunsky.designModel.simpleFactory.Car;

public class Dz implements Car{
    public void start() {
        System.out.println("Dz start");
    }

    public void run() {
        System.out.println("Dz run");
    }
}
