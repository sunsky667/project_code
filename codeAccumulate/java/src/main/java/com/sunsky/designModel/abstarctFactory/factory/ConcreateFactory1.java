package com.sunsky.designModel.abstarctFactory.factory;

import com.sunsky.designModel.abstarctFactory.IProductA;
import com.sunsky.designModel.abstarctFactory.IProductB;
import com.sunsky.designModel.abstarctFactory.impl.ProductA1;
import com.sunsky.designModel.abstarctFactory.impl.ProductB1;

public class ConcreateFactory1 extends Factory1{
    public IProductA getProductA1() {
        return new ProductA1();
    }

    public IProductB getProductB1() {
        return new ProductB1();
    }
}
