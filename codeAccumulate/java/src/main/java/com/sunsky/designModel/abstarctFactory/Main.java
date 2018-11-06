package com.sunsky.designModel.abstarctFactory;

import com.sunsky.designModel.abstarctFactory.factory.ConcreateFactory1;
import com.sunsky.designModel.abstarctFactory.factory.Factory1;

/**
 抽象工厂模式与工厂方法模式的最大区别就在于工厂方法模式针对的是一个产品等级结构；而抽象工厂模式则需要面对多个产品等级结构。

 假设一个子系统需要一些产品对象，而这些产品又属于一个以上的产品等级结构。那么为了将消费这些产品对象的责任和创建这些产品对象的责任分割开来，可以引进抽象工厂模式。这样的话，消费产品的一方不需要直接参与产品的创建工作，而只需要向一个公用的工厂接口请求所需要的产品。

 优点:
 （1）分离接口和实现
 　　客户端使用抽象工厂来创建需要的对象，而客户端根本就不知道具体的实现是谁，客户端只是面向产品的接口编程而已。也就是说，客户端从具体的产品实现中解耦。

 （2）使切换产品族变得容易
 　　因为一个具体的工厂实现代表的是一个产品族，比如上面例子的从A产品到B产品只需要切换一下具体工厂。

 缺点:
 （1）不太容易扩展新的产品
 　　如果需要给整个产品族添加一个新的产品，那么就需要修改抽象工厂，这样就会导致修改所有的工厂实现类。
 */
public class Main {
    public static void main(String[] args){

        Factory1 factory1 = new ConcreateFactory1();
        IProductA productA1 = factory1.getProductA1();
        IProductB productB1 = factory1.getProductB1();

        productA1.method();
        productB1.method();
    }
}
