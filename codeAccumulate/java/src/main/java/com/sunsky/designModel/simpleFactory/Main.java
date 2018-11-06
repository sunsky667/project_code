package com.sunsky.designModel.simpleFactory;


/**
 * 本文主要介绍简单工厂模式，主要通过代码的形式进行展示，对于程序员来说我想观看代码进行理解比文档来的直接和快些。
 简单工厂模式（simple factory）是类的创建模式，又叫静态工厂方法（static factory method）模式。
 简单工厂模式就是由一个工厂类根据传入的参数决定创建哪一种的产品类。
 */
public class Main {
    public static void main(String[] args){
        Car benz = CarFactory.getInstance("Benz");
        benz.run();
        benz.start();

        Car byd = CarFactory.getInstance("Byd");
        byd.start();
        benz.run();

        Car Dz = CarFactory.getInstance("Dz");
        Dz.start();
        Dz.run();
    }
}
