package com.sunsky.designModel.abstarctFactory.factory;

import com.sunsky.designModel.abstarctFactory.IProductA;
import com.sunsky.designModel.abstarctFactory.IProductB;
import com.sunsky.designModel.abstarctFactory.impl.ProductA2;
import com.sunsky.designModel.abstarctFactory.impl.ProductB2;

public class ConcreateFactory2 extends Factory2{
    public IProductA getProductA2() {
        return new ProductA2();
    }

    public IProductB getProductB2() {
        return new ProductB2();
    }
}
