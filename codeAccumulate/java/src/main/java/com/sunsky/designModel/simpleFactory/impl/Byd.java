package com.sunsky.designModel.simpleFactory.impl;

import com.sunsky.designModel.simpleFactory.Car;

public class Byd implements Car{
    public void start() {
        System.out.println("BYD start");
    }

    public void run() {
        System.out.println("BYD run");
    }
}
